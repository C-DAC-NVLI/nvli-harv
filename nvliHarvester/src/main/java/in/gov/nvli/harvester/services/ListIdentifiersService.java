/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.ResumptionTokenType;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public interface ListIdentifiersService {
  
  public void getListIdentifiers(String baseUrl) throws MalformedURLException, IOException, JAXBException;
  public void getRecordFromIdentifiers(String baseUrl,String identifier,String metadataPrefix);
  
}
