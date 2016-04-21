/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public interface HarvesterService {
  
  public void harvestReposiotires(String baseURL) throws MalformedURLException, IOException, JAXBException, ParseException;
  public void harvestAllRepositories() throws MalformedURLException, IOException, JAXBException, ParseException;
  
}
