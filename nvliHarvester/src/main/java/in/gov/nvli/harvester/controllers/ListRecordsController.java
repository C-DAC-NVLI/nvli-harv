/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListRecordsService;
import java.io.IOException;                                                                                                                                                                                                                                             
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author richa
 */
@Controller
@EnableAsync
public class ListRecordsController {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ListRecordsController.class);
  
  @Autowired
  public ListRecordsService listRecordsService;
  
  @Autowired
  RepositoryDao repositoryDao;
  

  @RequestMapping("/listrecords")
  public String listRecord(@RequestParam("baseURL") String baseURL,@RequestParam("metadataPrefix") String metadataPrefix) {
    String requestURL = baseURL+"?verb="+VerbType.LIST_RECORDS.value()+"&metadataPrefix="+metadataPrefix;
    System.err.println("base url" + requestURL);
    HarRepo harRepo=repositoryDao.getRepository(baseURL);
    try {
      listRecordsService.setHarRepo(harRepo);
      listRecordsService.setMetadataPrefix(metadataPrefix);
      listRecordsService.getListRecord(requestURL);
    } catch (MalformedURLException ex) {
      LOGGER.error(ex.getMessage(),ex);
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(),ex);;
    } catch (JAXBException ex) {
      LOGGER.error(ex.getMessage(),ex);;
    } catch (ParseException ex) {
      LOGGER.error(ex.getMessage(),ex);;
    }
    return "example";
  }
  
}
