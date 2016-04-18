/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.servicesImpl.GetRecordServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author richa
 */
@Controller
public class GetRecordController {

  @Autowired
  public GetRecordService getRecordService;

  @RequestMapping("/getrecord")
  public String getRecord(@RequestParam("baseURL") String baseURL,@RequestParam("identifier") String identifier,@RequestParam("metadataPrefix") String metadataPrefix) {
    
    String requestURL = baseURL+"?verb="+VerbType.GET_RECORD.value()+"&identifier="+identifier+"&metadataPrefix="+metadataPrefix;
    System.err.println("request url" + requestURL);
    try {
      getRecordService.getRecord(requestURL);
    } catch (MalformedURLException ex) {
      Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(IdentifyController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JAXBException ex) {
      Logger.getLogger(GetRecordController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
      Logger.getLogger(GetRecordController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "example";
  }
}
