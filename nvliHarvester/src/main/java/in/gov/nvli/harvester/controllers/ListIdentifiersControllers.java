/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.services.ListIdentifiersService;
import in.gov.nvli.harvester.servicesImpl.ListIdentifiersServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author richa
 */
@Controller
public class ListIdentifiersControllers {

  private ListIdentifiersService listIdentifiersService;
  private String baseURL = "";

  @RequestMapping("/listidentifiers")
  public String listIdentifiers() {
    // baseURL=baseURL+"verb="+(VerbType.IDENTIFY).toString().toLowerCase();
    baseURL = "http://dspace.library.iitb.ac.in/oai/request?verb=ListIdentifiers&metadataPrefix=oai_dc";
    System.err.println("base url" + baseURL);
    try {
      listIdentifiersService = new ListIdentifiersServiceImpl();
      listIdentifiersService.getListIdentifiers(baseURL);
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
