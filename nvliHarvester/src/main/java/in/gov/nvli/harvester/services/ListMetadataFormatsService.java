/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.customised.MethodEnum;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
public interface ListMetadataFormatsService {

    public boolean saveHarMetadataTypes(HarRepo reposotory, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException, OAIPMHerrorTypeException;

    public List<MetadataFormatType> getMetadataFormatTypeList(HttpURLConnection connection, String desiredURL) throws IOException, JAXBException, OAIPMHerrorTypeException;

}
