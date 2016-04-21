/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.services.HarvesterService;
import java.io.IOException;
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
 * @author vootla
 */
@Controller
public class HarvesterController {

  @Autowired
  HarvesterService harvesterService;

  @RequestMapping("/")
  public String exampleMethod() {
    return "example";
  }

  @RequestMapping("/harvester")
  public String harvestRepository(@RequestParam("baseURL") String baseURL) {
    try {
      harvesterService.harvestReposiotires(baseURL);
    } catch (IOException ex) {
      Logger.getLogger(HarvesterController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JAXBException ex) {
      Logger.getLogger(HarvesterController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
      Logger.getLogger(HarvesterController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "example";
  }

  @RequestMapping("/harvestall")
  public String harvestAll() {
    try {
      harvesterService.harvestAllRepositories();
    } catch (IOException ex) {
      Logger.getLogger(HarvesterController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JAXBException ex) {
      Logger.getLogger(HarvesterController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
      Logger.getLogger(HarvesterController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "example";
  }

}
