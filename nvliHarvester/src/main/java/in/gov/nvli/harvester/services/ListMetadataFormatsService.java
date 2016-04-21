/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.beans.HarRepo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
public interface ListMetadataFormatsService {
      public int getConnectionStatus(String  baseURL,String method,String userAgnet,String adminEmail) throws MalformedURLException, IOException;
      public List<MetadataFormatType> getListMetadataFormats() throws IOException,JAXBException;
       public boolean saveListOfMetadataFormats() throws IOException, JAXBException;
        public List<MetadataFormatType> getListMetadataFormats(String baseUrl) throws MalformedURLException,IOException,JAXBException;
       public boolean saveListOfMetadataFormats(String baseUrl) throws MalformedURLException,IOException, JAXBException;
        public void setRepository(HarRepo repository);
}
