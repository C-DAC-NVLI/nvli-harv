/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.MethodEnum;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public interface ListIdentifiersService {

    public void getListIdentifiers(String basUrl, MethodEnum method, String adminEmail, String metadataPrefix) throws MalformedURLException, IOException, JAXBException;
            
    public void getListIdentifiers(HarRepo repository, MethodEnum method, String adminEmail, String metadataPrefix) throws MalformedURLException, IOException, JAXBException;

    public void getRecordByIdentifiers(HarRepo repository, MethodEnum method, String adminEmail, String identifier, String metadataPrefix);

}
