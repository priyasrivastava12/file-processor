package com.file.orchestrator.client;

import com.file.orchestrator.dao.FileMetaDataRepository;
import com.file.orchestrator.exception.APIException;
import com.file.orchestrator.model.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileMatcherServiceClient {

  private FileMetaDataRepository fileMetaDataRepository;

  private  WebClient webClient;

  private final Logger logger = Logger.getLogger(FileMatcherServiceClient.class.getName());


  @Autowired
  public FileMatcherServiceClient(FileMetaDataRepository fileMetaDataRepository) {
    this.fileMetaDataRepository=fileMetaDataRepository;
  }

  public void processFiles(String buyerFile, String supplierFile,String uuid) {
    try {
      webClient = WebClient.create("http://localhost:8001");
      webClient.get()
              .uri(uriBuilder -> uriBuilder
                      .path("/processFiles")
                      .queryParam("buyerFile", buyerFile)
                      .queryParam("supplierFile", supplierFile)
                      .queryParam("taskID",uuid)
                      .build())
              .retrieve()
              .toBodilessEntity()
              .block();
      logger.log(Level.INFO, "API call succeeded with status code: 202");
      //fileMetaDataRepository.saveFileMetaData(uuid,buyerFile,supplierFile, FileStatus.WAITING);
    } catch (WebClientResponseException ex) {
      int statusCode = ex.getRawStatusCode();
      if (statusCode != 202) {
        logger.log(Level.SEVERE, "API call failed with status code: " + statusCode + ", Message: " + ex.getMessage());
        throw new APIException(statusCode, "API call failed with status code: " + statusCode, ex);
      }
    }
  }
}
