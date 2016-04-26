/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import javax.servlet.ServletContext;



/**
 *
 * @author richa
 */
public interface HarvesterService {
  
  public void harvestReposiotires(String baseURL,ServletContext servletContext);
  public void harvestAllRepositories(ServletContext servletContext);
  
}
