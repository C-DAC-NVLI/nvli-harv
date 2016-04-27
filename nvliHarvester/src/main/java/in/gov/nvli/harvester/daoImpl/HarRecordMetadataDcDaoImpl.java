/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import java.io.Serializable;
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
public class HarRecordMetadataDcDaoImpl extends GenericDaoImpl<HarRecordMetadataDc, Long> implements HarRecordMetadataDcDao {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(HarRecordMetadataDcDaoImpl.class);

    public HarRecordMetadataDcDaoImpl() {
        super(HarRecordMetadataDc.class);
    }

  

  @Override
  @Transactional
  public void save(HarRecordMetadataDc metadataDc) {
    Session session = null;
    try {
      session = currentSession();
      session.save(metadataDc);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(),e);
    }
  }

  @Override
  @Transactional
  public void saveList(List<HarRecordMetadataDc> metadataDcs) {
    Session session = null;
    try {
      session = currentSession();
      for (HarRecordMetadataDc metadataDc : metadataDcs) {
        session.save(metadataDc);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(),e);
    }
  }

    @Override
    @Transactional
    public boolean saveOrUpdateList(List<HarRecordMetadataDc> metadataDcs) {
        HarRecordMetadataDc tempHarRecordMetadataDc = null;
        try{
            for (HarRecordMetadataDc metadataDc : metadataDcs) {
                tempHarRecordMetadataDc = GetByHarRecord(metadataDc.getRecordId());
                if(tempHarRecordMetadataDc != null){
                    metadataDc.setRecordMetadataDcId(tempHarRecordMetadataDc.getRecordMetadataDcId());
                    currentSession().merge(metadataDc);
                }else{
                    if (!createNew(metadataDc)) {
                        return false; 
                    }
                }
            }
            return true;
        }catch(Exception e){
           LOGGER.error(e.getMessage(),e);
           return false;
        }
    }

    @Override
    public HarRecordMetadataDc GetByHarRecord(HarRecord harRecord) {
        try{
            return (HarRecordMetadataDc) currentSession().createCriteria(HarRecordMetadataDc.class).createAlias("recordId", "harRecord").add(Restrictions.eq("harRecord.recordId", harRecord.getRecordId())).uniqueResult();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

}
