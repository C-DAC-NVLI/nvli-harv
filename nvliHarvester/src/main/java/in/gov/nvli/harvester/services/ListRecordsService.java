/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import com.sun.syndication.io.FeedException;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataTypeEnum;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import java.io.IOException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author richa
 */
public interface ListRecordsService {

    public boolean saveListRecords(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail);

    public boolean saveListRecords(String baseURL, HarRecordMetadataTypeEnum harRecordMetadataTypeObj, MethodEnum method, String adminEmail);

    public boolean saveOrUpdateListRecords(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException;

    public boolean saveOrUpdateListRecords(String baseURL, HarRecordMetadataTypeEnum harRecordMetadataTypeObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException;

    public boolean saveListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException;

    public boolean saveListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException;

    public boolean saveOrUpdateListHarRecordData(String baseURL, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException;

    public boolean saveOrUpdateListHarRecordData(HarRepo harRepoObj, MethodEnum method, String adminEmail) throws OAIPMHerrorTypeException, ParseException, JAXBException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException;

    public boolean isListRecordsAvailable(HarRepo harRepoObj, MethodEnum method, String adminEmail, String metadataPrefix);

    public boolean saveListRecordsXML(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail, boolean saveDataInFileSystem);

    public boolean saveOrUpdateListRecordsXML(HarRepoMetadata harRepoMetadataObj, MethodEnum method, String adminEmail, boolean saveDataInFileSystem);

    public boolean saveHarRecordDataInFileSystem(HarRepo harRepoObj, HarRecordMetadataTypeEnum harRecordMetadataTypeObj);

}
