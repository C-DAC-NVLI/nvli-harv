/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.services.HarvesterService;
import java.io.IOException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author vootla
 */
@Controller
@EnableAsync
public class HarvesterController {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HarvesterController.class);
  
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
      LOGGER.error(ex.getMessage(),ex);
    } catch (JAXBException ex) {
      LOGGER.error(ex.getMessage(),ex);;
    } catch (ParseException ex) {
      LOGGER.error(ex.getMessage(),ex);;
    }
    return "example";
  }

  @RequestMapping("/harvestall")
  public String harvestAll() {
    try {
      harvesterService.harvestAllRepositories();
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
