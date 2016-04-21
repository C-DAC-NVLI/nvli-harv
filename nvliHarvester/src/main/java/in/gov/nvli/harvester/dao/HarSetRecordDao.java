/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.beans.HarSetRecord;
import java.util.List;

/**
 *
 * @author richa
 */
public interface HarSetRecordDao extends GenericDao<HarSetRecord, Long>{
  
  public boolean saveHarSetRecords(List<HarSetRecord> setRecords);
  
}
