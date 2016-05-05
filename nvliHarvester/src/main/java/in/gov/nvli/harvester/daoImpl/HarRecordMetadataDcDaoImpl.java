/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOrWrite;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author richa
 */
@Repository
@TransactionalReadOnly
public class HarRecordMetadataDcDaoImpl extends GenericDaoImpl<HarRecordMetadataDc, Long> implements HarRecordMetadataDcDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HarRecordMetadataDcDaoImpl.class);

    @Autowired
    private HarRecordDao harRecordDao;
    
    public HarRecordMetadataDcDaoImpl() {
        super(HarRecordMetadataDc.class);
    }

    @Override
    @TransactionalReadOrWrite
    public boolean saveOrUpdateHarRecordMetadataDcList(List<HarRecordMetadataDc> metadataDcs) {
        HarRecordMetadataDc tempHarRecordMetadataDc = null;
        HarRecord harRecord=null;
        
        try {
            for (HarRecordMetadataDc metadataDc : metadataDcs) {
                if(metadataDc.getRecordId().getRecordId() == null){
                    harRecord = harRecordDao.getHarRecordByRecordIdentifier(metadataDc.getRecordId().getRecordIdentifier());
                    metadataDc.setRecordId(harRecord);
                }
                tempHarRecordMetadataDc = GetByHarRecord(metadataDc.getRecordId());
                if (tempHarRecordMetadataDc != null) {
                    if(tempHarRecordMetadataDc.getRecordId().getRecordSoureDatestamp().compareTo(metadataDc.getRecordId().getRecordSoureDatestamp()) < 0){
                        metadataDc.setRecordMetadataDcId(tempHarRecordMetadataDc.getRecordMetadataDcId());
                        currentSession().merge(metadataDc);
                    }
                } else if (!createNew(metadataDc)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public HarRecordMetadataDc GetByHarRecord(HarRecord harRecord) {
        try {
            return (HarRecordMetadataDc) currentSession().createCriteria(HarRecordMetadataDc.class).createAlias("recordId", "harRecord").add(Restrictions.eq("harRecord.recordId", harRecord.getRecordId())).uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

}
