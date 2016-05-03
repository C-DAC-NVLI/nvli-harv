/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.services.ListIdentifiersService;
import in.gov.nvli.harvester.servicesImpl.ListIdentifiersServiceImpl;
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
public class ListIdentifiersControllers {

  private ListIdentifiersService listIdentifiersService;
  private String baseURL = "";

  @RequestMapping("/listidentifiers")
  public String listIdentifiers(@RequestParam("baseURL") String baseURL,@RequestParam("metadataPrefix") String metadataPrefix) {
        
    String requestURL = baseURL+"?verb="+VerbType.LIST_IDENTIFIERS.value()+"&metadataPrefix="+metadataPrefix;
    System.err.println("base url" + requestURL);
    try {
      listIdentifiersService = new ListIdentifiersServiceImpl();
      listIdentifiersService.getListIdentifiers(requestURL, MethodEnum.GET, "", "oai_dc");
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
      Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      ex.printStackTrace();
      Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JAXBException ex) {
      ex.printStackTrace();
      Logger.getLogger(GetRecordController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "example";
  }

}
