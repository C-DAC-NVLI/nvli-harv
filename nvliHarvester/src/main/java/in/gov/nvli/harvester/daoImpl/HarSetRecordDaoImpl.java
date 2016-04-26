/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richa
 */
@Repository
@Transactional(readOnly = true)
public class HarSetRecordDaoImpl extends GenericDaoImpl<HarSetRecord, Long> implements HarSetRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HarSetRecordDaoImpl.class);
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

  
  @Override
  @Transactional
  public boolean saveOrUpdateHarSetRecords(List<HarSetRecord> setRecords) {
    HarSetRecord tempHarSetRecord = null;
      try {
      for (HarSetRecord setRecord : setRecords) {
        tempHarSetRecord = getHarSetRecord(setRecord.getRecordId(), setRecord.getSetId());
        if(tempHarSetRecord != null){
            setRecord.setSetRecordId(tempHarSetRecord.getSetRecordId());
            currentSession().merge(setRecord);
        }else{
            if (!createNew(setRecord)) {
                return false; 
            }
        }
      }
      return true;
    } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        return false;
    }
  }
  
    @Override
    public HarSetRecord getHarSetRecord(HarRecord harRecord, HarSet harSet) {
        try{
            return (HarSetRecord) currentSession().createCriteria(HarSetRecord.class).createAlias("recordId", "harRecord").createAlias("setId", "harSet").add(Restrictions.and(Restrictions.eq("harRecord.recordId", harRecord.getRecordId()),Restrictions.eq("harSet.setId", harSet.getSetId()))).uniqueResult();
        }catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        
    }

}
