/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public interface GetRecordService {
  
  public void getRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException;
  
}
