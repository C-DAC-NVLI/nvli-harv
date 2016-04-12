/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public interface IdentifyService {
    
    public int getConnectionStatus(String  baseURL,String method,String userAgnet,String adminEmail) throws MalformedURLException, IOException;
    public OAIPMHtype getRepositoryInformation() throws IOException,JAXBException;
    
    
}
