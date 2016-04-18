/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.services.ListRecordsService;
import in.gov.nvli.harvester.servicesImpl.ListRecordsServiceImpl;
import java.io.IOException;                                                                                                                                                                                                                                             
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author richa
 */
@Controller
public class ListRecordsController {
  
  public ListRecordsService listRecordsService;
  private String baseURL = "";

  @RequestMapping("/listrecords")
  public String listRecord(@RequestParam("baseURL") String baseURL,@RequestParam("metadataPrefix") String metadataPrefix) {
    baseURL="http://export.arxiv.org/oai2";
    metadataPrefix="oai_dc";
    String requestURL = baseURL+"?verb="+VerbType.LIST_RECORDS+"&metadataPrefix="+metadataPrefix;
    System.err.println("base url" + requestURL);
    try {
      listRecordsService = new ListRecordsServiceImpl();
      listRecordsService.getListRecord(requestURL);
    } catch (MalformedURLException ex) {
      Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JAXBException ex) {
      Logger.getLogger(GetRecordController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "example";
  }
  
}
