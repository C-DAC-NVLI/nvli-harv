/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.HarMetadataTypeRepositoryDao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vootla
 */
@Repository
@Transactional(readOnly = true)
public class HarMetadataTypeRepositoryDaoImpl extends GenericDaoImpl<HarMetadataTypeRepository, Short> implements HarMetadataTypeRepositoryDao {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HarMetadataTypeRepositoryDaoImpl.class);
    
    public HarMetadataTypeRepositoryDaoImpl() {
        super(HarMetadataTypeRepository.class);
    }
    
    @Override
    public boolean saveHarMetadataTypesOfRepository(List<HarMetadataTypeRepository> metadataTypesOfRepo) {
        try {
            for (HarMetadataTypeRepository metadata : metadataTypesOfRepo) {
                if (getMetadataTypeRepository(metadata.getMetadataTypeId(), metadata.getRepoId()) != null) {
                    continue;                    
                }
                
                if (!createNew(metadata)) {
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
    public HarMetadataTypeRepository getMetadataTypeRepository(HarMetadataType metadataType, HarRepo repository) {
        
        HarMetadataTypeRepository harMetadataTypeRepository = null;
        try {
            harMetadataTypeRepository = (HarMetadataTypeRepository) currentSession().createCriteria(HarMetadataTypeRepository.class).createAlias("metadataTypeId", "metadataType").createAlias("repoId", "repo").add(Restrictions.and(Restrictions.eq("metadataType.metadataId", metadataType.getMetadataId()), Restrictions.eq("repo.repoId", repository.getRepoId()))).uniqueResult();            
            
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return harMetadataTypeRepository;
    }
    
}
