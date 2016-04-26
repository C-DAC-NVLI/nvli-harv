/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.dao.HarRecordDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richa
 */
@Repository
@Transactional(readOnly = true)
public class HarRecordDaoImpl extends GenericDaoImpl<HarRecord, Long> implements HarRecordDao {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(HarRecordDaoImpl.class);

    public HarRecordDaoImpl() {
        super(HarRecord.class);
    }

  

  @Override
  @Transactional
  public void saveHarRecord(HarRecord record) {
    Session session = null;
    try {
      session = currentSession();
      session.save(record);

    } catch (Exception e) {
      LOGGER.error(e.getMessage(),e);
    }
  }

  @Override
  @Transactional
  public void saveListHarRecord(List<HarRecord> records) {
    Session session = null;
    try {
      session = currentSession();
      for (HarRecord record : records) {
        session.save(record);
      }

    } catch (Exception e) {
      LOGGER.error(e.getMessage(),e);
    }
  }
  
  @Override
  @Transactional
  public void saveOrUpdateListHarRecord(List<HarRecord> records) {
    HarRecord temprecord = null;
    try {
      for (HarRecord record : records) {
          temprecord = getHarRecord(record.getRecordIdentifier());
          if(temprecord != null){
              record.setRecordId(temprecord.getRecordId());
              currentSession().merge(record);
          }else{
              createNew(record);
          }    
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(),e);
    }
  }

    @Override
    public HarRecord getHarRecord(String recordIdentifier) {
        try{
            return (HarRecord) currentSession().createCriteria(HarRecord.class).add(Restrictions.eq("recordIdentifier", recordIdentifier)).uniqueResult();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }
  
  

}
