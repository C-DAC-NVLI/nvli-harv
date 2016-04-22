/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richa
 */
@Repository
@Transactional(readOnly = true)
public class HarSetRecordDaoImpl extends GenericDaoImpl<HarSetRecord, Long> implements HarSetRecordDao {

  public HarSetRecordDaoImpl() {
    super(HarSetRecord.class);
  }

  @Override
  @Transactional
  public boolean saveHarSetRecords(List<HarSetRecord> setRecords) {
    try {
      for (HarSetRecord setRecord : setRecords) {
        if (!createNew(setRecord)) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
