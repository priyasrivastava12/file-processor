package com.file.upload.dao;

import com.file.upload.model.FileCompareMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserMetaDataRepository {

  @Autowired
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


  @Autowired
  public UserMetaDataRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.namedParameterJdbcTemplate = jdbcTemplate;
  }

  public void saveFileCompareMetaData(FileCompareMetaData metaData) {
    String sql = "INSERT INTO user_metadata " +
            "(buyer_file_path, supplier_file_path, user_name, email_id, mobile_number) " +
            "VALUES (:buyerFilePath, :supplierFilePath, :userName, :emailId, :mobileNumber)";

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("buyerFilePath", metaData.getBuyerFilePath());
    paramMap.put("supplierFilePath", metaData.getSupplierFilePath());
    paramMap.put("userName", metaData.getUserName());
    paramMap.put("emailId", metaData.getEmailId());
    paramMap.put("mobileNumber", metaData.getMobileNumber());

    namedParameterJdbcTemplate.update(sql, paramMap);
  }
}
