package com.file.matcher.parser;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.file.matcher.matcher.FileMatcher;
import com.file.matcher.model.*;
import com.file.matcher.service.KafkaPublisherService;
import com.file.matcher.util.DataUtil;
import com.file.matcher.util.ScoreUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDataParser {

  @Autowired
  KafkaPublisherService kafkaPublisherService;

  @Autowired
  AmazonS3 amazonS3;
  @Value("${aws.bucketName}")
  private String bucketName;

  ScoreUtils scoreUtil;

  @Autowired
  FileMatcher fileMatcher;

  public void processFile(File buyer, File supplier, String taskID) {


    List<Row> buyerData = convertFileToData(buyer);
    List<Row> supplierData = convertFileToData(supplier);
    List<Result> results = new ArrayList<>();

    List<Row> leftoverSupplierData = new ArrayList<>();

    for (Row row : buyerData) {
      //TODO:// add threshold from user
      Result result = fileMatcher.match(row, supplierData, 200);
      results.add(result);
    }
    List<Integer> supplierIndexprocessed = new ArrayList<>();


    //getting processed supplier index
    for (Result result : results) {
      if (result.getSupplierIndex() >= 0)
        supplierIndexprocessed.add(result.getSupplierIndex());
    }

    //finding suppliers which are not processed
    for (int index = 0; index < supplierData.size(); index++) {
      if (!supplierIndexprocessed.contains(index)) {
        leftoverSupplierData.add(supplierData.get(index));
      }
    }

    //handling supplier data which matched with no row
    for (Row row : leftoverSupplierData) {
      Result result = new Result();
      result.setSeller(row);
      result.setScore(Double.MAX_VALUE);
      results.add(result);
    }

    for (Result result : results) {
      ScoreUtils.enrichCategoryFromScore(result);// refactored
    }

    File resultFile = convertToFile(results);

    try {
      uploadFileToS3(resultFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    String key = "output";
    URL presignedUrl = amazonS3.generatePresignedUrl(bucketName, key + "/" + resultFile.getName(), /*expirationTime*/ null);


    FileResultMetaData fileResultMetaData = new FileResultMetaData();
    fileResultMetaData.setOutputfilePath(presignedUrl.toString());

    fileResultMetaData.setTaskId(taskID);
    kafkaPublisherService.publishEvent(fileResultMetaData);
  }

  private String uploadFileToS3(File file) throws IOException {

    // Create a File object from the MultipartFile
    String key = "output";


    // Upload the file to S3
    PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(bucketName, key + "/" + file.getName(), file));

    //Files.delete(Path.of(file.getPath()));
    return putObjectResult.getMetadata().toString();
    // Delete the temporary file

  }


  private File convertToFile(List<Result> results) {

    List<String> headers = getHeader(results.get(0).getBuyer());

    List<String> originalHeades = new ArrayList<>(headers);
    headers.add("Category");
    headers.addAll(getHeader(results.get(0).getBuyer()));
    String[] headerarray = new String[headers.size()];


    headers.toArray(headerarray);

    String path = "/Users/solenoid/Documents/processor/file-processor/output.csv";
    try (Writer writer = new FileWriter(path)) {
      try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
              .withHeader(headerarray))) {

        for (Result result : results) {
          Row buyer = result.getBuyer();
          Row seller = result.getSeller();

          List<Object> objects = new ArrayList<>();

          for (String header : originalHeades) {
            if (buyer != null && buyer.getRow().get(new DataKey(header)) != null)
              objects.add(buyer.getRow().get(new DataKey(header)).getValue());
            else {
              objects.add("");
            }

          }
          objects.add(result.getCategory());

          for (String header : originalHeades) {
            if (seller != null && seller.getRow().get(new DataKey(header)) != null)
              objects.add(seller.getRow().get(new DataKey(header)).getValue());
            else {
              objects.add("");
            }
          }


          Object[] arrayObject = new Object[objects.size()];
          objects.toArray(arrayObject);

          csvPrinter.printRecord(arrayObject);
        }
        csvPrinter.flush();
        System.out.println("Objects written to CSV successfully.");

      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new File(path);

  }

  private List<String> getHeader(Row row) {
    List<String> headers = new ArrayList<>();
    for (DataKey dataKey : row.getRow().keySet()) {
      headers.add(dataKey.getKey());
    }
    return headers;
  }

  private static DataType checkDataTypeAndFormat(String value, CSVRecord csvRecord, String header) {
    if (DataUtil.isNumeric(value)) {
      return DataType.NUMERIC;
    } else if (DataUtil.isDate(value)) {
      return DataType.DATE;
    } else {
      return DataType.STRING;
    }

  }

  public List<Row> convertFileToData(File file) {

    try (Reader reader = new FileReader(file);
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {


      List<Row> data = new ArrayList<>();
      // Assuming the first row in the CSV file contains column headers
      for (CSVRecord csvRecord : csvParser) {
        // Iterate through each column
        Row row = new Row();
        for (String header : csvRecord.toMap().keySet()) {
          String value = csvRecord.get(header);

          // Check data type
          DataType dataType = checkDataTypeAndFormat(value, csvRecord, header);

          DataKey dataKey = new DataKey();
          dataKey.setKey(header);
          DataValue dataValue = new DataValue();
          dataValue.setDataType(dataType);
          dataValue.setValue(value);

          row.getRow().put(dataKey, dataValue);

          System.out.println("Column '" + header + "' contains " + dataType + " data: " + value);
        }
        data.add(row);
      }
      return data;
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }


}
