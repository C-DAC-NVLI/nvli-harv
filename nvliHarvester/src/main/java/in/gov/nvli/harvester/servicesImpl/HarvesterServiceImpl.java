/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.HarvesterService;
import in.gov.nvli.harvester.services.IdentifyService;
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.services.ListSetsService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class HarvesterServiceImpl implements HarvesterService {

    @Autowired
    ListSetsService listSetsService;

    @Autowired
    ListMetadataFormatsService listMetadataFormatsService;

    @Autowired
    ListRecordsService listRecordsService;

    @Autowired
    RepositoryDao repositoryDao;

    @Override
    @Async
    public void harvestRepository(String baseURL) throws MalformedURLException, IOException, JAXBException, ParseException {

        HarRepo harRepo = repositoryDao.getRepository(baseURL);

        listSetsService.saveListSets(baseURL + "?verb=" + VerbType.LIST_SETS.value());

        listMetadataFormatsService.setRepository(harRepo);
        listMetadataFormatsService.saveListOfMetadataFormats(baseURL + "?verb=" + VerbType.LIST_METADATA_FORMATS.value());

        listRecordsService.setHarRepo(harRepo);
        listRecordsService.setMetadataPrefix("oai_dc");
        listRecordsService.getListRecord(baseURL + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=oai_dc");

    }

    @Override
    @Async
    public void harvestAllRepositories() throws MalformedURLException, IOException, JAXBException, ParseException {
        List<HarRepo> harRepos = repositoryDao.getRepositories();
        if (harRepos != null) {
            for (HarRepo harRepo : harRepos) {
              listSetsService.saveListSets(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_SETS.value());

              listMetadataFormatsService.setRepository(harRepo);
              listMetadataFormatsService.saveListOfMetadataFormats(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_METADATA_FORMATS.value());

              listRecordsService.setHarRepo(harRepo);
              listRecordsService.setMetadataPrefix("oai_dc");
              listRecordsService.getListRecord(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=oai_dc");
            }
        }
    }

    @Override
    @Async
    public void harvestRepositoryIncremental(String baseURL) throws MalformedURLException, IOException, JAXBException, ParseException {
        
        HarRepo harRepo = repositoryDao.getRepository(baseURL);

        listSetsService.saveOrUpdateListSets(baseURL + "?verb=" + VerbType.LIST_SETS.value());

        listMetadataFormatsService.setRepository(harRepo);
        listMetadataFormatsService.saveListOfMetadataFormats(baseURL + "?verb=" + VerbType.LIST_METADATA_FORMATS.value());

        listRecordsService.setHarRepo(harRepo);
        listRecordsService.setMetadataPrefix("oai_dc");
        listRecordsService.setIncrementalUpdateFlag(true);
        listRecordsService.getListRecord(baseURL + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=oai_dc");
    }

    @Override
    @Async
    public void harvestAllRepositoriesIncremental() throws MalformedURLException, IOException, JAXBException, ParseException {
        List<HarRepo> harRepos = repositoryDao.getRepositories();
        if (harRepos != null) {
            for (HarRepo harRepo : harRepos) {
                listSetsService.saveOrUpdateListSets(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_SETS.value());

                listMetadataFormatsService.setRepository(harRepo);
                listMetadataFormatsService.saveListOfMetadataFormats(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_METADATA_FORMATS.value());

                listRecordsService.setHarRepo(harRepo);
                listRecordsService.setMetadataPrefix("oai_dc");
                listRecordsService.setIncrementalUpdateFlag(true);
                listRecordsService.getListRecord(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=oai_dc");
            }
        }
    }

}
