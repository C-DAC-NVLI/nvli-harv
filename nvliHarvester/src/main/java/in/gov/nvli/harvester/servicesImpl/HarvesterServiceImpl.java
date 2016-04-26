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
import in.gov.nvli.harvester.services.ListMetadataFormatsService;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.services.ListSetsService;
import java.util.List;
import javax.servlet.http.HttpSession;
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
  @Async
  public void harvestReposiotires(String baseURL, HttpSession session) {

    HarRepo harRepo = repositoryDao.getRepository(baseURL);
    try {
      listSetsService.saveListSets(baseURL + "?verb=" + VerbType.LIST_SETS.value());

      listMetadataFormatsService.setRepository(harRepo);
      listMetadataFormatsService.saveListOfMetadataFormats(baseURL + "?verb=" + VerbType.LIST_METADATA_FORMATS.value());

      listRecordsService.setSession(session);
      listRecordsService.setHarRepo(harRepo);
      listRecordsService.setMetadataPrefix("oai_dc");
      listRecordsService.getListRecord(baseURL + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=oai_dc");
    } catch (Exception ex) {
      ex.printStackTrace();
      LOGGER.error(ex.getMessage(),ex);
    }

  }

  @Override
  @Async
  public void harvestAllRepositories(HttpSession session) {
    List<HarRepo> harRepos = repositoryDao.getRepositories();
    if (harRepos != null) {
      for (HarRepo harRepo : harRepos) {
        try {
          listSetsService.saveListSets(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_SETS.value());

          listMetadataFormatsService.setRepository(harRepo);

          listMetadataFormatsService.saveListOfMetadataFormats(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_METADATA_FORMATS.value());

          listRecordsService.setSession(session);
          listRecordsService.setHarRepo(harRepo);
          listRecordsService.setMetadataPrefix("oai_dc");

          listRecordsService.getListRecord(harRepo.getRepoBaseUrl() + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=oai_dc");
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(),ex);
        }
      }
    }
  }

}
