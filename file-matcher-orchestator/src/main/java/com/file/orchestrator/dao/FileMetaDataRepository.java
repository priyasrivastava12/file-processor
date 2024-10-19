package com.file.orchestrator.dao;

import com.file.orchestrator.model.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileMetaDataRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public FileMetaDataRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public void saveFileMetaData(String uuid, String buyerFile,
                               String supplierFile, FileStatus fileStatus) {
    String sql = "INSERT INTO file_metadata " +
            "(buyer_file_path, supplier_file_path, taskid,status) " +
            "VALUES (:buyerFilePath, :supplierFilePath, :taskid, :status)";

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("buyerFilePath", buyerFile);
    paramMap.put("supplierFilePath", supplierFile);
    paramMap.put("taskid", uuid);
    paramMap.put("status", fileStatus.name());
    namedParameterJdbcTemplate.update(sql, paramMap);
  }


  /*  CREATE TABLE file_metadata (
        taskid VARCHAR(255),
        buyer_file_path VARCHAR(255),
        supplier_file_path VARCHAR(255),
        status VARCHAR(255),
        timestamp ;
    );*/

}
