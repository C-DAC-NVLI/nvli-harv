/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import com.sun.syndication.io.FeedException;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.customised.MethodEnum;
import java.io.IOException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author richa
 */
public interface ListRecordsService {

    public boolean saveListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException;

    public boolean saveListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException;

    public boolean saveOrUpdateListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException;

    public boolean saveOrUpdateListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException;

    public boolean saveListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, IOException;

    public boolean saveListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, IOException;

    public boolean saveOrUpdateListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, IOException;

    public boolean saveOrUpdateListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, IOException;

}
