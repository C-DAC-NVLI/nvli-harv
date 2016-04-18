/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRecord;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author richa
 */
public interface HarRecordDao {
  
  public void saveHarRecord(HarRecord record);
  public void saveListHarRecord(List<HarRecord> records);
  
}
