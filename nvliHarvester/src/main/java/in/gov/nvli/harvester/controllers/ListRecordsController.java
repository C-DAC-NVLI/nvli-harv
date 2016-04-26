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
import javax.servlet.http.HttpServletRequest;
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

  @Autowired
  public ListRecordsService listRecordsService;

  @Autowired
  RepositoryDao repositoryDao;

  @RequestMapping("/listrecords")
  public String listRecord(@RequestParam("baseURL") String baseURL, @RequestParam("metadataPrefix") String metadataPrefix, HttpServletRequest servletRequest) {
    String requestURL = baseURL + "?verb=" + VerbType.LIST_RECORDS.value() + "&metadataPrefix=" + metadataPrefix;
    System.err.println("base url" + requestURL);
    HarRepo harRepo = repositoryDao.getRepository(baseURL);
    listRecordsService.setServletContext(servletRequest.getServletContext());
    listRecordsService.setHarRepo(harRepo);
    listRecordsService.setMetadataPrefix(metadataPrefix);
    listRecordsService.getListRecord(requestURL);
    return "example";
  }

}
