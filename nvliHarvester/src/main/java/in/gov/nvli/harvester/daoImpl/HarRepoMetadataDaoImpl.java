/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.beans.HarRepoStatus;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOrWrite;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import in.gov.nvli.harvester.dao.HarRepoMetadataDao;
import in.gov.nvli.harvester.dao.HarRepoStatusDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ankit
 */
@Repository
@TransactionalReadOnly
public class HarRepoMetadataDaoImpl extends GenericDaoImpl<HarRepoMetadata, Integer> implements HarRepoMetadataDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HarRepoMetadataDaoImpl.class);

    @Autowired
    private HarRepoStatusDao harRepoStatusDaoObj;
    
    @Autowired
    private RepositoryDao repositoryDaoObj;

    public HarRepoMetadataDaoImpl() {
        super(HarRepoMetadata.class);
    }

    @Override
    public HarRepoMetadata get(int harRepoId, HarRecordMetadataType harRecordMetadataTypeObj) {
        return (HarRepoMetadata) currentSession()
                .createCriteria(HarRepoMetadata.class)
                .createAlias("repoId", "harRepo")
                .createAlias("metadataTypeId", "metadataType")
                .add(Restrictions.and(
                        Restrictions.eq("harRepo.repoId", harRepoId),
                        Restrictions.eq("metadataType.metadataPrefix", harRecordMetadataTypeObj.value())
                )
                ).uniqueResult();

    }
    
    @Override
    public HarRepoMetadata get(String repoUID, HarRecordMetadataType harRecordMetadataTypeObj) {
        return (HarRepoMetadata) currentSession()
                .createCriteria(HarRepoMetadata.class)
                .createAlias("repoId", "harRepo")
                .createAlias("metadataTypeId", "metadataType")
                .add(Restrictions.and(
                        Restrictions.eq("harRepo.repoUID", repoUID),
                        Restrictions.eq("metadataType.metadataPrefix", harRecordMetadataTypeObj.value())
                )
                ).uniqueResult();

    }

    @Override
    @TransactionalReadOrWrite
    public boolean updateStatus(HarRepoMetadata harRepoMetadataObj, short status) {
        try {
            HarRepoStatus harRepoStatusObj = harRepoStatusDaoObj.get(status);

            harRepoMetadataObj.setHarvestStatus(harRepoStatusObj);
            merge(harRepoMetadataObj);
        } catch (Exception ex) {
            LOGGER.error("Activity --> error while updating status of HarRepoMetadata ID : " + harRepoMetadataObj.getRepoMetadataId()
                    + ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    @Override
    @TransactionalReadOrWrite
    public void updateStartTime(HarRepoMetadata harRepoMetadataObj, Date updatedDate) {
        harRepoMetadataObj.setHarvestStartTime(updatedDate);
        merge(harRepoMetadataObj);
    }

    @Override
    @TransactionalReadOrWrite
    public void updateEndTime(HarRepoMetadata harRepoMetadataObj, Date updatedDate) {
        harRepoMetadataObj.setHarvestEndTime(updatedDate);
        merge(harRepoMetadataObj);
    }
    
    @Override
    @TransactionalReadOrWrite
    public boolean changeRepositoryMetadataStatus(HarRepo harRepoObj, short status) {
        List<HarRepoMetadata> harRepoMetadataList = list(harRepoObj);
        if(harRepoMetadataList != null){
            HarRepoStatus harRepoStatusObj = harRepoStatusDaoObj.get(status);
            for(HarRepoMetadata tempHarRepoMetadata : harRepoMetadataList){
                tempHarRepoMetadata.setHarvestStatus(harRepoStatusObj);
                merge(tempHarRepoMetadata);
            }
        }
        return true;
    }
    
    @Override
    @TransactionalReadOrWrite
    public boolean changeRepositoryMetadataStatus(String repoUID, short status) {
        HarRepo harRepoObj = repositoryDaoObj.getRepositoryByUID(repoUID);
        List<HarRepoMetadata> harRepoMetadataList = list(harRepoObj);
        if(harRepoMetadataList != null){
            HarRepoStatus harRepoStatusObj = harRepoStatusDaoObj.get(status);
            for(HarRepoMetadata tempHarRepoMetadata : harRepoMetadataList){
                tempHarRepoMetadata.setHarvestStatus(harRepoStatusObj);
                merge(tempHarRepoMetadata);
            }
        }
        return true;
    }
    
    
    @Override
    public List<HarRepoMetadata> list(HarRepo harRepoObj) {
        return currentSession()
                .createCriteria(HarRepoMetadata.class)
                .createAlias("repoId", "harRepo")
                .add(Restrictions.eq("harRepo.repoId", harRepoObj.getRepoId()))
                .list();
                
    }

    @Override
    public List<HarRepoMetadata> list(String repoUID) {
        return currentSession()
                .createCriteria(HarRepoMetadata.class)
                .createAlias("repoId", "harRepo")
                .add(Restrictions.eq("harRepo.repoId", repoUID))
                .list();
                
    }
}
