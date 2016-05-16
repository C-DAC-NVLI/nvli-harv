/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.dao.HarRecordDataDao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ankit
 */
@Repository
@TransactionalReadOnly
public class HarRecordDataDaoImpl extends GenericDaoImpl<HarRecordData, Long>implements HarRecordDataDao{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HarMetadataTypeDaoImpl.class);
    
    public HarRecordDataDaoImpl() {
        super(HarRecordData.class);
    }
    
    @Override
    public List<HarRecordData> list(HarRepo harRepoObject){
        try {
        return currentSession().createCriteria(HarRecordData.class)
                .createAlias("recordId", "harRecordAlias")
                .add(Restrictions.eq("harRecordAlias.repoId", harRepoObject))
                .list();
        }catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
    
}
