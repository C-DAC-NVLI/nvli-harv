/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.customised.RepoStatusEnum;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.services.ListSetsService;
import java.util.List;
import java.util.concurrent.Future;
import javax.servlet.ServletContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class HarvesterServiceImpl implements HarvesterService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HarvesterServiceImpl.class);

    @Autowired
    ListSetsService listSetsService;

    @Autowired
    ListMetadataFormatsService listMetadataFormatsService;

    @Autowired
    ListRecordsService listRecordsService;

    @Autowired
    RepositoryDao repositoryDao;

    @Override
    public boolean harvestRepository(String baseURL, ServletContext servletContext) {
        
        HarRepo harRepo = repositoryDao.getRepository(baseURL);
         if(!harvesterConstraintChecker(harRepo, (byte)0))
        {
            LOGGER.info("can't Start Harvesting("+harRepo.getRepoUID()+") :::"+harRepo.getRepoStatusId().getRepoStatusName());  
            return  false;
        }
        
        try {
            harvestRepository(harRepo, servletContext);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    @Override
    @Async
    public Future<String> harvestRepositoryByUID(String repoUID, ServletContext servletContext) {

        HarRepo harRepo = repositoryDao.getRepositoryByUID(repoUID);
        if(!harvesterConstraintChecker(harRepo, (byte)0))
        {
            LOGGER.info("can't Start Harvesting("+harRepo.getRepoUID()+") ::: "+harRepo.getRepoStatusId().getRepoStatusName());  
            return new AsyncResult<String>("Can't Start Hravesting("+harRepo.getRepoUID()+") ::: "+harRepo.getRepoStatusId().getRepoStatusName());  
        }
         
        try {
            harvestRepository(harRepo, servletContext);
            
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
           return new AsyncResult<String>("ERROR");
        }
       return new AsyncResult<String>("STARTED");
    }

    private void harvestRepository(HarRepo harRepo, ServletContext servletContext) {
       
        try {
            listSetsService.saveHarSets(harRepo, MethodEnum.GET, "");
            listMetadataFormatsService.saveHarMetadataTypes(harRepo, MethodEnum.GET, "");

            listRecordsService.saveListRecords(harRepo, "oai_dc", MethodEnum.GET, "", servletContext);
            
            if(harRepo.getOreEnableFlag()==1)
            {
                listRecordsService.saveListHarRecordData(harRepo, MethodEnum.GET, "", servletContext);
            }
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_COMPLETE.getValue());
            
            
        } catch (Exception ex) {
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_PROCESSING_ERROR.getValue());
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    @Override
    @Async
    public void harvestAllRepositories(ServletContext servletContext) {
        List<HarRepo> harRepos = repositoryDao.list();
        harvestRepositories(harRepos, servletContext);
    }

    @Override
     @Async
    public void harvestRepositories(List<HarRepo> harRepos, ServletContext servletContext) {
        if (harRepos != null) {
            for (HarRepo harRepo : harRepos) {
               
                if(!harvesterConstraintChecker(harRepo, (byte)0))
                {
                   LOGGER.info("can't Start Harvesting("+harRepo.getRepoUID()+") ::: "+harRepo.getRepoStatusId().getRepoStatusName()); 
                     continue;
                }
                
                try {
                    harvestRepository(harRepo, servletContext);
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    @Async
    public void harvestRepositoryIncremental(String baseURL, ServletContext servletContext) {

        HarRepo harRepo = repositoryDao.getRepository(baseURL);
        harvestRepositoryIncremental(harRepo,servletContext);

    }
    
    @Override
    @Async
    public void harvestRepositoryIncrementalBYUID(String repoUID, ServletContext servletContext) {

        HarRepo harRepo = repositoryDao.getRepositoryByUID(repoUID);
        harvestRepositoryIncremental(harRepo,servletContext);

    }
    
    
    
    private void harvestRepositoryIncremental( HarRepo harRepo, ServletContext servletContext) {
        
        if(!harvesterConstraintChecker(harRepo, (byte)0))
                {
                   LOGGER.info("can't Start Harvesting("+harRepo.getRepoUID()+") ::: "+harRepo.getRepoStatusId().getRepoStatusName()); 
                   return ;
                }
        
        try {
            listSetsService.saveOrUpdateHarSets(harRepo, MethodEnum.GET, "");

            listMetadataFormatsService.saveHarMetadataTypes(harRepo, MethodEnum.GET, "");

            listRecordsService.saveListRecords(harRepo, "oai_dc", MethodEnum.GET, "", servletContext);
            
            if(harRepo.getOreEnableFlag()==1)
            {
                //start ore harvesting
            }
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_COMPLETE.getValue());
        } catch (Exception ex) {
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getValue());
            LOGGER.error(ex.getMessage(), ex);
        }

    }
    
    
    

    @Override
    public void harvestAllRepositoriesIncremental(ServletContext servletContext) {
        List<HarRepo> harRepos = repositoryDao.list();
       harvestRepositoriesIncremental(harRepos,servletContext);
    }

    
    @Override
    @Async
    public void harvestRepositoriesIncremental(List<HarRepo> harRepos,ServletContext servletContext) {
        if (harRepos != null) {
            for (HarRepo harRepo : harRepos) {
                try {
                    harvestRepositoryIncremental(harRepo, servletContext);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
    
    
    private boolean harvesterConstraintChecker(HarRepo repo,byte firstOrIncremental)//0-firstTime,1-Incremental
    {
        switch(repo.getRepoStatusId().getRepoStatusId())
        {
            case 1://not_active
                return false;
            case 2://active
                //change status code to  harvest_processing(i.e 3)
                return repositoryDao.changeRepoStatus(repo.getRepoUID(),RepoStatusEnum.HARVEST_PROCESSING.getValue());
            case 3://harvest_processing
               return false;
            case 4://harvest_processing_error
                 return  false;
            case 5://harvest_complete
                if(firstOrIncremental==1)
                {
                    return repositoryDao.changeRepoStatus(repo.getRepoUID(),RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getValue());
                }  
                else
                 return  false;
            case 6://increment_harvest_processing
               return false;
            case 7://increment_harvest_processing_error
               return false;
            case 8://invalid_url
                return false;
            default:
                return false;

                    
                    
                            
                            
        }
        
        
    }
    
    
}
