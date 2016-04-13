/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.AboutType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.daoImpl.HarRecordDaoImpl;
import in.gov.nvli.harvester.daoImpl.HarRecordMetadataDcDaoImpl;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class GetRecordServiceImpl implements GetRecordService {

  private HttpURLConnection connection;

  @Override
  public void getRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException {
    connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();
    String response = OAIResponseUtil.createResponseFromXML(connection);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    System.out.println("Identifier " + getRecordObj.getGetRecord().getRecord().getHeader().getIdentifier());
    
    HarRecord record=new HarRecord();
    record.setIdentifier(getRecordObj.getGetRecord().getRecord().getHeader().getIdentifier());
    record.setMetadataTypeId(new HarMetadataType());
    List<AboutType> aboutTypes=getRecordObj.getGetRecord().getRecord().getAbout();
    String temp="";
    for (AboutType about : aboutTypes) {
      temp+=about;
    }
    record.setAbout(temp);
   //save record object in db
    HarRecordDao recordDao=new HarRecordDaoImpl();
    recordDao.saveHarRecord(record);
    //end
    
    HarRecordMetadataDc recordMetadataDc=new HarRecordMetadataDc();
    recordMetadataDc.setRecordId(record);
    getMetadataFromObj(getRecordObj.getGetRecord().getRecord().getMetadata().getOaidc(), recordMetadataDc);
    
    //save metadata object in db
    HarRecordMetadataDcDao metadataDcDao=new HarRecordMetadataDcDaoImpl();
    metadataDcDao.save(recordMetadataDc);
    //end
    

  }

  public HarRecordMetadataDc getMetadataFromObj(OAIDC oaiDC,HarRecordMetadataDc recordMetadataDc) {
    List<String> titles = oaiDC.getTitle();
    List<String> creators = oaiDC.getCreator();
    List<String> subjects = oaiDC.getSubject();
    List<String> descriptions = oaiDC.getDescription();
    List<String> dates = oaiDC.getDate();
    List<String> types = oaiDC.getType();
    List<String> identifiers = oaiDC.getIdentifier();
    List<String> contributors = oaiDC.getContributor();
    List<String> coverages = oaiDC.getCoverage();
    List<String> languages = oaiDC.getLanguage();
    List<String> publishers = oaiDC.getPublisher();
    List<String> relations = oaiDC.getRelation();
    List<String> rights = oaiDC.getRights();
    List<String> sources = oaiDC.getSource();
    List<String> formats = oaiDC.getFormat();
    
    recordMetadataDc.setTitle(getMetadataTagValueSeparatedBySpecialChar(titles));
    recordMetadataDc.setCreator(getMetadataTagValueSeparatedBySpecialChar(creators));
    recordMetadataDc.setSubject(getMetadataTagValueSeparatedBySpecialChar(subjects));
    recordMetadataDc.setDescription(getMetadataTagValueSeparatedBySpecialChar(descriptions));
    recordMetadataDc.setDate(getMetadataTagValueSeparatedBySpecialChar(dates));
    recordMetadataDc.setType(getMetadataTagValueSeparatedBySpecialChar(types));
    recordMetadataDc.setIdentifier(getMetadataTagValueSeparatedBySpecialChar(identifiers));
    recordMetadataDc.setContributor(getMetadataTagValueSeparatedBySpecialChar(contributors));
    recordMetadataDc.setCoverage(getMetadataTagValueSeparatedBySpecialChar(coverages));
    recordMetadataDc.setLanguage(getMetadataTagValueSeparatedBySpecialChar(languages));
    recordMetadataDc.setPublisher(getMetadataTagValueSeparatedBySpecialChar(publishers));
    recordMetadataDc.setRelation(getMetadataTagValueSeparatedBySpecialChar(relations));
    recordMetadataDc.setRights(getMetadataTagValueSeparatedBySpecialChar(rights));
    recordMetadataDc.setSource(getMetadataTagValueSeparatedBySpecialChar(sources));
    recordMetadataDc.setFormat(getMetadataTagValueSeparatedBySpecialChar(formats));
    
    return recordMetadataDc;
  }
  
  public String getMetadataTagValueSeparatedBySpecialChar(List<String> tagValues){
    
    String columnValue="";
    for (String tagValue : tagValues) {
      columnValue+=tagValue+CommonConstants.COLUMNVALUESEPARARTOR;
    }
    
    return columnValue;
  }


}
