/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.servicesImpl.GetRecordServiceImpl;
import in.gov.nvli.harvester.servicesImpl.IdentifyServiceImpl;
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
public class GetRecordController {

  public GetRecordService getRecordService;
  private String baseURL = "";

  @RequestMapping("/getrecord")
  public String getRecord() {
    // baseURL=baseURL+"verb="+(VerbType.IDENTIFY).toString().toLowerCase();
    baseURL = "http://export.arxiv.org/oai2?verb=GetRecord&identifier=oai:arXiv.org:cs/0112017&metadataPrefix=oai_dc";
    System.err.println("base url" + baseURL);
    try {
      getRecordService = new GetRecordServiceImpl();
      getRecordService.getRecord(baseURL);
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
