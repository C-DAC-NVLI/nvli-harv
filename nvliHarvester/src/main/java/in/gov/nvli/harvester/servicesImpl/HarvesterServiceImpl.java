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
import in.gov.nvli.harvester.restClient.RepositoryClient;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.services.ListSetsService;
import in.gov.nvli.harvester.utilities.DatesRelatedUtil;
import in.gov.nvli.harvester.utilities.HarvesterLogUtils;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import org.slf4j.Logger;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(HarvesterServiceImpl.class);

    @Autowired
    private ListSetsService listSetsService;

    @Autowired
    private ListMetadataFormatsService listMetadataFormatsService;

    @Autowired
    private ListRecordsService listRecordsService;

    @Autowired
    private RepositoryDao repositoryDao;

    @Autowired
    private RepositoryClient repositoryClient;
    
    @Override
    public boolean harvestRepository(String baseURL) {

        HarRepo harRepo = repositoryDao.getRepository(baseURL);
        if (!harvesterConstraintChecker(harRepo, false)) {
            LOGGER.info("can't Start Harvesting(" + harRepo.getRepoUID() + ") :::" + harRepo.getRepoStatusId().getRepoStatusName());
            return false;
        }

        try {
            harvestRepository(harRepo);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    @Override
    @Async
    public Future<String> harvestRepositoryByUID(String repoUID) {

        HarRepo harRepo = repositoryDao.getRepositoryByUID(repoUID);
        if (!harvesterConstraintChecker(harRepo, false)) {
            LOGGER.info("can't Start Harvesting(" + harRepo.getRepoUID() + ") ::: " + harRepo.getRepoStatusId().getRepoStatusName());
            return new AsyncResult<String>("Can't Start Hravesting(" + harRepo.getRepoUID() + ") ::: " + harRepo.getRepoStatusId().getRepoStatusName());
        }

        try {
            harvestRepository(harRepo);

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new AsyncResult<String>("ERROR");
        }
        return new AsyncResult<String>("STARTED");
    }

    private void harvestRepository(HarRepo harRepo) {

        try {
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.HARVESTING_STARTED);
            Date beforeHarvestingDate = new Date();

            listSetsService.saveHarSets(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.SETS_SAVED);

            listMetadataFormatsService.saveHarMetadataTypes(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.METADATAFORMATS_SAVED);

            listRecordsService.saveListRecords(harRepo, "oai_dc", MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.LISTRECORDS_SAVED);

            if (harRepo.getOreEnableFlag() == 1) {
                LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.ORE_HARVESTING_STARTED);
                listRecordsService.saveListHarRecordData(harRepo, MethodEnum.GET, "");
                LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.ORE_HARVESTING_FINISHED);
            }

            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.GETTING_RECORD_COUNT);
            repositoryDao.updateHarRecordCount(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.RECORD_COUNT_UPDATED);

            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_COMPLETE.getId());
            repositoryDao.updateLastSyncStartDate(harRepo.getRepoUID(), DatesRelatedUtil.getDateInUTCFormat(beforeHarvestingDate));
            repositoryDao.updateLastSyncEndDate(harRepo.getRepoUID(), DatesRelatedUtil.getCurrentDateTimeInUTCFormat());
            
            harRepo =  repositoryDao.getRepositoryByUID(harRepo.getRepoUID());
            repositoryClient.synRepoWithClient(harRepo);
            
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.HARVESTING_FINISHED);
           
            
        } catch (Exception ex) {
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_PROCESSING_ERROR.getId());
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    @Override
    @Async
    public void harvestAllRepositories() {
        List<HarRepo> harRepos = repositoryDao.list();
        harvestRepositories(harRepos);
    }

    @Override
    @Async
    public void harvestAllActiveRepositories(List<HarRepo> harRepos) {
        harvestRepositories(harRepos);
    }

    @Override
    @Async
    public void harvestRepositories(List<HarRepo> harRepos) {
        if (harRepos != null) {
            for (HarRepo harRepo : harRepos) {

                if (!harvesterConstraintChecker(harRepo, false)) {
                    LOGGER.info("can't Start Harvesting(" + harRepo.getRepoUID() + ") ::: " + harRepo.getRepoStatusId().getRepoStatusName());
                    continue;
                }

                try {
                    harvestRepository(harRepo);
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    @Async
    public void harvestRepositoryIncremental(String baseURL) {

        HarRepo harRepo = repositoryDao.getRepository(baseURL);
        harvestRepositoryIncremental(harRepo);

    }

    @Override
    @Async
    public void harvestRepositoryIncrementalBYUID(String repoUID) {

        HarRepo harRepo = repositoryDao.getRepositoryByUID(repoUID);
        harvestRepositoryIncremental(harRepo);

    }

    private void harvestRepositoryIncremental(HarRepo harRepo) {

        if (!harvesterConstraintChecker(harRepo, true)) {
            LOGGER.info("can't Start Harvesting(" + harRepo.getRepoUID() + ") ::: " + harRepo.getRepoStatusId().getRepoStatusName());
            return;
        }

        try {
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.INCREMENTAL_HARVESTING_STARTED);
            Date beforeHarvestingDate = new Date();

            listSetsService.saveOrUpdateHarSets(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.SETS_SAVED);
            
            listMetadataFormatsService.saveHarMetadataTypes(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.METADATAFORMATS_SAVED);
            
            listRecordsService.saveOrUpdateListRecords(harRepo, "oai_dc", MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.LISTRECORDS_SAVED);
            
            if (harRepo.getOreEnableFlag() == 1) {
                LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.ORE_INCREMENTAL_HARVESTING_STARTED);
                listRecordsService.saveOrUpdateListHarRecordData(harRepo, MethodEnum.GET, "");
                LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.ORE_INCREMENTAL_HARVESTING_FINISHED);
            }
            
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.GETTING_RECORD_COUNT);
            repositoryDao.updateHarRecordCount(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.RECORD_COUNT_UPDATED);
            
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_COMPLETE.getId());
            repositoryDao.updateLastSyncStartDate(harRepo.getRepoUID(), DatesRelatedUtil.getDateInUTCFormat(beforeHarvestingDate));
            repositoryDao.updateLastSyncEndDate(harRepo.getRepoUID(), DatesRelatedUtil.getCurrentDateTimeInUTCFormat());
            
            harRepo =  repositoryDao.getRepositoryByUID(harRepo.getRepoUID());
            repositoryClient.synRepoWithClient(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogUtils.INCREMENTAL_HARVESTING_FINISHED);

        } catch (Exception ex) {
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getId());
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    @Override
    @Async
    public void harvestAllRepositoriesIncremental() {
        List<HarRepo> harRepos = repositoryDao.list();
        harvestRepositoriesIncremental(harRepos);
    }

    @Override
    @Async
    public void harvestRepositoriesIncremental(List<HarRepo> harRepos) {
        if (harRepos != null) {
            for (HarRepo harRepo : harRepos) {
                try {
                    harvestRepositoryIncremental(harRepo);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    private boolean harvesterConstraintChecker(HarRepo repo, boolean incrementalFlag)//0-firstTime,1-Incremental
    {
        switch (repo.getRepoStatusId().getRepoStatusId()) {
            case 1://not_active
                return false;
            case 2://active
                //change status code to  harvest_processing(i.e 3)
                return repositoryDao.changeRepoStatus(repo.getRepoUID(), RepoStatusEnum.HARVEST_PROCESSING.getId());
            case 3://harvest_processing
                return false;
            case 4://harvest_processing_error
                return false;
            case 5://harvest_complete
                if (incrementalFlag) {
                    return repositoryDao.changeRepoStatus(repo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getId());
                } else {
                    return false;
                }
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
