/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOrWrite;
import in.gov.nvli.harvester.dao.HarRecordDao;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author richa
 */
@Repository
@TransactionalReadOnly
public class HarRecordDaoImpl extends GenericDaoImpl<HarRecord, Long> implements HarRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HarRecordDaoImpl.class);

    public HarRecordDaoImpl() {
        super(HarRecord.class);
    }

    @Override
    @TransactionalReadOrWrite
    public void saveOrUpdateHarRecordList(List<HarRecord> records) {
        for (HarRecord record : records) {
            saveOrUpdateHarRecord(record);
        }
    }

    @Override
    @TransactionalReadOrWrite
    public void saveOrUpdateHarRecord(HarRecord harRecordObj) {
        HarRecord tempHarRecord = null;
        try {
            tempHarRecord = getHarRecordByRecordIdentifier(harRecordObj.getRecordIdentifier());
            if (tempHarRecord != null) {
                if (tempHarRecord.getRecordSoureDatestamp().compareTo(harRecordObj.getRecordSoureDatestamp()) < 0) {
                    harRecordObj.setRecordId(tempHarRecord.getRecordId());
                    currentSession().merge(harRecordObj);
                }
            } else {
                createNew(harRecordObj);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public HarRecord getHarRecordByRecordIdentifier(String recordIdentifier) {
        try {
            return (HarRecord) currentSession().createCriteria(HarRecord.class).add(Restrictions.eq("recordIdentifier", recordIdentifier)).uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public long getCount(HarRepo harRepoObj) {
        try {
            return (long) currentSession().createCriteria(HarRecord.class)
                    .createAlias("repoId", "harRepo")
                    .add(Restrictions.eq("harRepo.repoId", harRepoObj.getRepoId()))
                    .setProjection(Projections.rowCount())
                    .uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public HarRecord getRecord(long recordId) {
        HarRecord record = null;
        try {
            record = get(recordId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        return record;
    }

    @Override
    public Long rowCount(short recordStaus) {
        return (Long) currentSession()
                .createCriteria(HarRecord.class)
                .add(Restrictions.eq("recordStatus", recordStaus))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }
    
    @Override
    public Long rowCount(HarRepo harRepoObj, short recordStaus) {
        return (Long) currentSession()
                .createCriteria(HarRecord.class)
                .createAlias("repoId", "harRepo")
                .add(Restrictions.eq("harRepo.repoId", harRepoObj.getRepoId()))
                .add(Restrictions.eq("recordStatus", recordStaus))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public List<HarRecord> list(int displayStart, int displayLength, short recordStatus) {
        return currentSession()
                .createCriteria(HarRecord.class)
                .addOrder(Order.asc("recordId"))
                .add(Restrictions.eq("recordStatus", recordStatus))
                .setFirstResult(displayStart)
                .setMaxResults(displayLength)
                .list();
    }
    
    @Override
    public List<HarRecord> list(HarRepo harRepoObj, int displayStart, int displayLength, short recordStatus) {
        return currentSession()
                .createCriteria(HarRecord.class)
                .createAlias("repoId", "harRepo")
                .addOrder(Order.asc("recordId"))
                .add(Restrictions.eq("harRepo.repoId", harRepoObj.getRepoId()))
                .add(Restrictions.eq("recordStatus", recordStatus))
                .setFirstResult(displayStart)
                .setMaxResults(displayLength)
                .list();
    }
}
