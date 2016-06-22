/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import com.sun.syndication.io.FeedException;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.customised.HarRecordDataCustomised;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author richa
 */
public interface GetRecordService {

    public boolean saveGetRecord(String baseURL, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public boolean saveGetRecord(HarRepo repository, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public boolean saveOrUpdateGetRecord(HarRepo harRepoObj, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public boolean saveGetRecordList(List<RecordType> recordTypeList, HarRepo repository, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public HarRecordMetadataDc convertOAIDCToHarRecordMetadataDc(OAIDC oaiDC);

    public HarRecord getHarRecordByRecordType(RecordType recordTypeObject, String metadataPrefix, HarRepo harRepoObject) throws ParseException;

    public List<HarSetRecord> getHarSetRecordListByRecordType(RecordType recordTypeObject, HarRecord harRecordObject);

    public HarRecordMetadataDc getHarRecordMetadataDcByRecordType(RecordType recordTypeObject, HarRecord harRecordObject);

    public HarRecordData getHarRecordDataByRecordType(RecordType recordTypeObject, String metadataPrefix, HarRepo harRepoObject, boolean incrementalFlag) throws ParseException, TransformerConfigurationException, TransformerException, IOException, IllegalArgumentException, FeedException, MalformedURLException, JAXBException;

    public void saveHarRecordDataInFileSystem(HarRecordData harRecordDataObj, String path) throws IOException;

    public HarRecord getRecord(long recordId);

    public HarRecordMetadataDc GetMetaDataByHarRecord(HarRecord harRecord);

    public HarRecord getHarRecordByRecordType(RecordType recordTypeObject, HarRepo harRepoObject, boolean incrementalFlag) throws IOException, MalformedURLException, JAXBException, ParseException;

    public boolean saveHarRecordDataInFileSystem(HarRepo harRepoObj, HarRecordMetadataType harRecordMetadataTypeObj);

    public List<HarRecordDataCustomised> readHarRecordDataFromXML(HarRepo harRepoObj, HarRecordMetadataType harRecordMetadataTypeObj, List<HarRecord> harRecordList) throws SAXException, IOException, TransformerException, TransformerConfigurationException, IllegalArgumentException, FeedException, ParserConfigurationException, JAXBException;
}
