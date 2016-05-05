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
 * @author vootla
 */
public interface ListMetadataFormatsService {

    public boolean saveHarMetadataTypes(HarRepo reposotory, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException;

}
