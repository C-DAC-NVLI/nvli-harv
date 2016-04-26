/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;

import in.gov.nvli.harvester.services.HarvesterService;
import javax.servlet.http.HttpServletRequest;
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

  @Autowired
  HarvesterService harvesterService;

  @RequestMapping("/")
  public String exampleMethod() {
    return "example";
  }

  @RequestMapping("/harvester")
  public String harvestRepository(@RequestParam("baseURL") String baseURL, HttpServletRequest servletRequest) {
    harvesterService.harvestReposiotires(baseURL, servletRequest.getSession());

    return "example";
  }

  @RequestMapping("/harvestall")
  public String harvestAll(HttpServletRequest servletRequest) {
    harvesterService.harvestAllRepositories(servletRequest.getSession());
    return "example";
  }

}
