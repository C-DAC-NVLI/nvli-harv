/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.ListSetsType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
public interface ListSetsService {
      public int getConnectionStatus(String  baseURL,String method,String userAgnet,String adminEmail) throws MalformedURLException, IOException;
      public List<SetType> getListSets() throws IOException,JAXBException;
}
