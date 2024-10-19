package com.file.notification.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateFileMetaDataRepository {

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


  @Autowired
  public UpdateFileMetaDataRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public void updateFileMetadata(String taskidToUpdate, String newStatus, String newOutputFileLink) {
    String updateSql = "UPDATE file_metadata SET status = :status, outputfilelink = :outputfilelink WHERE taskid = :taskid";

    MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("status", newStatus)
            .addValue("outputfilelink", newOutputFileLink)
            .addValue("taskid", taskidToUpdate);

    int rowsUpdated = namedParameterJdbcTemplate.update(updateSql, parameters);

    if (rowsUpdated > 0) {
      System.out.println("Update successful. Rows updated: " + rowsUpdated);
    } else {
      System.out.println("No rows were updated.");
    }
  }

}
