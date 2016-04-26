/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import javax.servlet.http.HttpSession;


/**
 *
 * @author richa
 */
public interface HarvesterService {
  
  public void harvestReposiotires(String baseURL,HttpSession session);
  public void harvestAllRepositories(HttpSession session);
  
}
