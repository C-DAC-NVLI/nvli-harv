/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.custom.harvester_enum.RepoStatusEnum;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.restClient.RepositoryClient;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.services.ListSetsService;
import in.gov.nvli.harvester.utilities.DatesRelatedUtil;
import in.gov.nvli.harvester.constants.HarvesterLogConstants;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import in.gov.nvli.harvester.dao.HarRepoMetadataDao;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    @Autowired
    private HarRepoMetadataDao harRepoMetadataDaoObj;

    @Override
    public boolean harvestRepository(String baseURL) {

        HarRepo harRepo = repositoryDao.getRepository(baseURL);
        try {
            harvestRepository(harRepo);
        } catch (Exception ex) {
            LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                    + ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    @Override
    @Async
    public void harvestRepositoryByUID(String repoUID) {

        HarRepo harRepo = repositoryDao.getRepositoryByUID(repoUID);
        try {
            harvestRepository(harRepo);

        } catch (Exception ex) {
            LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                    + ex.getMessage(), ex);

        }

    }

    private void harvestRepository(HarRepo harRepo) {
        Date beforeHarvestingDate;
        HarRepoMetadata harRepoMetadataObj;
        boolean error = false;
        boolean dataSaved = false;

        try {

            if (!harvesterConstraintChecker(harRepo, false)) {
                LOGGER.error("can't Start Harvesting(" + harRepo.getRepoUID() + ") :::" + harRepo.getRepoStatusId().getRepoStatusName());
                repositoryClient.updateRepositoryStatus(harRepo);
                return;
            }

            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.HARVESTING_STARTED);
            beforeHarvestingDate = new Date();

            listSetsService.saveHarSets(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.SETS_SAVED);

            listMetadataFormatsService.saveHarMetadataTypes(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.METADATAFORMATS_SAVED);

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.OAI_DC);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveListRecords(harRepoMetadataObj, MethodEnum.GET, "")) {
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + " : " + HarRecordMetadataType.OAI_DC.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.OAI_DC.value() + " is not saved");
                    error = true;
                }
            }

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.ORE);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveListRecordsXML(harRepoMetadataObj, MethodEnum.GET, "", true)) {
                    dataSaved = true;
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + " : " + HarRecordMetadataType.ORE.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.ORE.value() + " is not saved");
                    error = true;
                }
            }

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.METS);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveListRecordsXML(harRepoMetadataObj, MethodEnum.GET, "", !dataSaved)) {
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + " : " + HarRecordMetadataType.METS.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.METS.value() + " is not saved");
                    error = true;
                }
            }

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.MARC);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveListRecordsXML(harRepoMetadataObj, MethodEnum.GET, "", false)) {
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + " : " + HarRecordMetadataType.MARC.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.MARC.value() + " is not saved");
                    error = true;
                }
            }

//            if (harRepo.getOreEnableFlag() == 1) {
//                LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.ORE_HARVESTING_STARTED);
//                if(listRecordsService.saveListHarRecordData(harRepo, MethodEnum.GET, "")){
//                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.ORE_HARVESTING_FINISHED);
//                }else{
//                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value()+ HarRecordMetadataType.ORE.value()+" is not saved");
//                }
//            }
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.GETTING_RECORD_COUNT);
            repositoryDao.updateHarRecordCount(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.RECORD_COUNT_UPDATED);

            if (error) {
                repositoryDao.changeRepoStatus(harRepo, RepoStatusEnum.HARVEST_PROCESSING_ERROR.getId());
            } else {
                repositoryDao.changeRepoStatus(harRepo, RepoStatusEnum.HARVEST_COMPLETE.getId());
                repositoryDao.updateLastSyncStartDate(harRepo, DatesRelatedUtil.getDateInUTCFormat(beforeHarvestingDate));
                repositoryDao.updateLastSyncEndDate(harRepo, DatesRelatedUtil.getCurrentDateTimeInUTCFormat());
            }

//            harRepo = repositoryDao.getRepositoryByUID(harRepo.getRepoUID());
//            repositoryClient.synRepoWithClient(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.HARVESTING_FINISHED);

        } catch (Exception ex) {
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.HARVEST_PROCESSING_ERROR.getId());
            LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                    + ex.getMessage(), ex);
            try {
                harRepo = repositoryDao.getRepositoryByUID(harRepo.getRepoUID());
                repositoryClient.updateRepositoryStatus(harRepo);
            } catch (URISyntaxException ex1) {
                LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                        + ex1.getMessage(), ex1);
            }
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
                try {
                    harvestRepository(harRepo);
                } catch (Exception ex) {
                    LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                            + ex.getMessage(), ex);
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
        Date beforeHarvestingDate;
        HarRepoMetadata harRepoMetadataObj;
        boolean error = false;
        boolean dataSaved = false;

        try {

            if (!harvesterConstraintChecker(harRepo, true)) {
                LOGGER.error("can't Start Incremental Harvesting(" + harRepo.getRepoUID() + ") ::: " + harRepo.getRepoStatusId().getRepoStatusName());
                repositoryClient.updateRepositoryStatus(harRepo);
                return;
            }

            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.INCREMENTAL_HARVESTING_STARTED);
            beforeHarvestingDate = new Date();

            listSetsService.saveOrUpdateHarSets(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.SETS_SAVED);

            listMetadataFormatsService.saveHarMetadataTypes(harRepo, MethodEnum.GET, "");
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.METADATAFORMATS_SAVED);

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.OAI_DC);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveOrUpdateListRecords(harRepoMetadataObj, MethodEnum.GET, "")) {
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + ":" + HarRecordMetadataType.OAI_DC.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.OAI_DC.value() + " is not saved");
                    error = true;
                }
            }

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.ORE);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveOrUpdateListRecordsXML(harRepoMetadataObj, MethodEnum.GET, "", true)) {
                    dataSaved = true;
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + ":" + HarRecordMetadataType.ORE.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.ORE.value() + " is not saved");
                    error = true;
                }
            }

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.METS);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveOrUpdateListRecordsXML(harRepoMetadataObj, MethodEnum.GET, "", !dataSaved)) {
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + ":" + HarRecordMetadataType.METS.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.METS.value() + " is not saved");
                    error = true;
                }
            }

            harRepoMetadataObj = harRepoMetadataDaoObj.get(harRepo.getRepoId(), HarRecordMetadataType.MARC);
            if (harRepoMetadataObj != null) {
                if (listRecordsService.saveOrUpdateListRecordsXML(harRepoMetadataObj, MethodEnum.GET, "", !dataSaved)) {
                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.LISTRECORDS_SAVED + ":" + HarRecordMetadataType.MARC.value());
                } else {
                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value() + " : " + HarRecordMetadataType.MARC.value() + " is not saved");
                    error = true;
                }
            }

//            if (harRepo.getOreEnableFlag() == 1) {
//                LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.ORE_INCREMENTAL_HARVESTING_STARTED);
//                if(listRecordsService.saveOrUpdateListHarRecordData(harRepo, MethodEnum.GET, "")){
//                    LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.ORE_INCREMENTAL_HARVESTING_FINISHED);
//                }else{
//                    LOGGER.error(harRepo.getRepoUID() + ":" + VerbType.LIST_RECORDS.value()+ HarRecordMetadataType.ORE.value()+" is not saved");
//                }
//            }
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.GETTING_RECORD_COUNT);
            repositoryDao.updateHarRecordCount(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.RECORD_COUNT_UPDATED);

            if (error) {
                repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getId());
            } else {
                repositoryDao.changeRepoStatus(harRepo, RepoStatusEnum.HARVEST_COMPLETE.getId());
                repositoryDao.updateLastSyncStartDate(harRepo, DatesRelatedUtil.getDateInUTCFormat(beforeHarvestingDate));
                repositoryDao.updateLastSyncEndDate(harRepo, DatesRelatedUtil.getCurrentDateTimeInUTCFormat());
            }

//            harRepo = repositoryDao.getRepositoryByUID(harRepo.getRepoUID());
//            repositoryClient.synRepoWithClient(harRepo);
            LOGGER.info(harRepo.getRepoUID() + ":" + HarvesterLogConstants.INCREMENTAL_HARVESTING_FINISHED);

        } catch (Exception ex) {
            repositoryDao.changeRepoStatus(harRepo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING_ERROR.getId());
            LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                    + ex.getMessage(), ex);
            try {
                harRepo = repositoryDao.getRepositoryByUID(harRepo.getRepoUID());
                repositoryClient.updateRepositoryStatus(harRepo);
            } catch (URISyntaxException ex1) {
                LOGGER.error("RepositoryUID --> " + harRepo.getRepoUID()
                        + ex1.getMessage(), ex1);
            }
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
                if (incrementalFlag) {
                    return false;
                } else {
                    //change status code to  harvest_processing(i.e 3)
                    return repositoryDao.changeRepoStatus(repo.getRepoUID(), RepoStatusEnum.HARVEST_PROCESSING.getId());
                }
            case 3://harvest_processing
                return false;
            case 4://harvest_processing_error
                if (incrementalFlag) {
                    //change status code to  increment_harvest_processing(i.e 6)
                    return repositoryDao.changeRepoStatus(repo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getId());
                } else {
                    return false;
                }
            case 5://harvest_complete
                if (incrementalFlag) {
                    //change status code to  increment_harvest_processing(i.e 6)
                    return repositoryDao.changeRepoStatus(repo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getId());
                } else {
                    return false;
                }
            case 6://increment_harvest_processing
                return false;
            case 7://increment_harvest_processing_error
                if (incrementalFlag) {
                    //change status code to  increment_harvest_processing(i.e 6)
                    return repositoryDao.changeRepoStatus(repo.getRepoUID(), RepoStatusEnum.INCREMENT_HARVEST_PROCESSING.getId());
                } else {
                    return false;
                }
            case 8://invalid_url
                return false;
            default:
                return false;

        }

    }

}
