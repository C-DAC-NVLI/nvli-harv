/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
//@Service
public interface IdentifyService {
    
    public int getConnectionStatus(String  baseURL,String method,String userAgnet,String adminEmail) throws MalformedURLException, IOException;
    public HarRepo getRepositoryInformation() throws IOException,JAXBException;
    public HarRepo getRepositoryInformation(String baseURL) throws MalformedURLException,IOException,JAXBException;
    
}
