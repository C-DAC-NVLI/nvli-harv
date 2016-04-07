/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
//@Service
public interface IdentifyService {
    
    public void getRepositoryInformation(String baseURL) throws ProtocolException, MalformedURLException, IOException;
    
    
}
