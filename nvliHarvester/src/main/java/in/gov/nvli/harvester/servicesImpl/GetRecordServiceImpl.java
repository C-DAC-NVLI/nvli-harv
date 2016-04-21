/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.AboutType;
import in.gov.nvli.harvester.OAIPMH_beans.GetRecordType;
import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.beans.HarSetRecord;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import in.gov.nvli.harvester.dao.HarRecordDao;
import in.gov.nvli.harvester.dao.HarRecordMetadataDcDao;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.dao.HarSetRecordDao;
import in.gov.nvli.harvester.daoImpl.HarRecordDaoImpl;
import in.gov.nvli.harvester.daoImpl.HarRecordMetadataDcDaoImpl;
import in.gov.nvli.harvester.services.GetRecordService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richa
 */
@Service
public class GetRecordServiceImpl implements GetRecordService {

  private HttpURLConnection connection;

  @Autowired
  private HarRecordMetadataDcDao metadataDcDao;

  @Autowired
  private HarRecordDao recordDao;

  @Autowired
  private HarMetadataTypeDao metadataTypeDao;
  HarSet harSet;

  @Autowired
  private HarSetDao harSetDao;

  @Autowired
  HarSetRecordDao harSetRecordDao;
  
  HarRepo harRepo;
  
  String metadataPrefix;

  @Override
  public void getRecord(String baseUrl) throws MalformedURLException, IOException, JAXBException, ParseException {
    connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
    int responseCode = connection.getResponseCode();
    String response = OAIResponseUtil.createResponseFromXML(connection);

    OAIPMHtype getRecordObj = UnmarshalUtils.xmlToOaipmh(response);

    System.out.println("Identifier " + getRecordObj.getGetRecord().getRecord().getHeader().getIdentifier());

    RecordType recordType = getRecordObj.getGetRecord().getRecord();

    HarRecord record = new HarRecord();
    record.setIdentifier(recordType.getHeader().getIdentifier());
    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
    Date sourceDate = formatter.parse(recordType.getHeader().getDatestamp());
    record.setSoureDatestamp(sourceDate);
    record.setMetadataTypeId(metadataTypeDao.getMetadataTypeByMetadatPrefix(metadataPrefix));
    record.setRepoId(harRepo);
    List<AboutType> aboutTypes = getRecordObj.getGetRecord().getRecord().getAbout();
    String temp = "";

    if (aboutTypes != null) {
      for (AboutType about : aboutTypes) {
        temp += about;
      }
    }
    record.setAbout(temp);
    //save record object in db
    recordDao.saveHarRecord(record);
    //end

    List<String> setSpecs = recordType.getHeader().getSetSpec();
    HarSetRecord harSetRecord;
    List<HarSetRecord> harSetRecords = new ArrayList<>();
    for (String setSpec : setSpecs) {
      harSet = harSetDao.getHarSet(setSpec);
      System.out.println("sets===" + harSet.getName());
      if (harSet != null) {
        harSetRecord = new HarSetRecord();
        harSetRecord.setSetId(harSet);
        harSetRecord.setRecordId(record);
        harSetRecords.add(harSetRecord);
      }
    }

    if (harSetRecords.size() != 0) {
      harSetRecordDao.saveHarSetRecords(harSetRecords);
    }

    HarRecordMetadataDc recordMetadataDc = new HarRecordMetadataDc();
    recordMetadataDc.setRecordId(record);
    getMetadataFromObj(getRecordObj.getGetRecord().getRecord().getMetadata().getOaidc(), recordMetadataDc);

    //save metadata object in db
    metadataDcDao.save(recordMetadataDc);
    //end

  }

  public HarRecordMetadataDc getMetadataFromObj(OAIDC oaiDC, HarRecordMetadataDc recordMetadataDc) {
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

    if (titles != null) {
      recordMetadataDc.setTitle(getMetadataTagValueSeparatedBySpecialChar(titles));
    }

    if (creators != null) {
      recordMetadataDc.setCreator(getMetadataTagValueSeparatedBySpecialChar(creators));
    }

    if (subjects != null) {
      recordMetadataDc.setSubject(getMetadataTagValueSeparatedBySpecialChar(subjects));
    }

    if (descriptions != null) {
      recordMetadataDc.setDescription(getMetadataTagValueSeparatedBySpecialChar(descriptions));
    }

    if (dates != null) {
      recordMetadataDc.setDate(getMetadataTagValueSeparatedBySpecialChar(dates));
    }

    if (types != null) {
      recordMetadataDc.setType(getMetadataTagValueSeparatedBySpecialChar(types));
    }

    if (identifiers != null) {
      recordMetadataDc.setIdentifier(getMetadataTagValueSeparatedBySpecialChar(identifiers));
    }

    if (contributors != null) {
      recordMetadataDc.setContributor(getMetadataTagValueSeparatedBySpecialChar(contributors));
    }

    if (coverages != null) {
      recordMetadataDc.setCoverage(getMetadataTagValueSeparatedBySpecialChar(coverages));
    }

    if (languages != null) {
      recordMetadataDc.setLanguage(getMetadataTagValueSeparatedBySpecialChar(languages));
    }

    if (publishers != null) {
      recordMetadataDc.setPublisher(getMetadataTagValueSeparatedBySpecialChar(publishers));
    }

    if (relations != null) {
      recordMetadataDc.setRelation(getMetadataTagValueSeparatedBySpecialChar(relations));
    }

    if (rights != null) {
      recordMetadataDc.setRights(getMetadataTagValueSeparatedBySpecialChar(rights));
    }

    if (sources != null) {
      recordMetadataDc.setSource(getMetadataTagValueSeparatedBySpecialChar(sources));
    }

    if (formats != null) {
      recordMetadataDc.setFormat(getMetadataTagValueSeparatedBySpecialChar(formats));
    }
    return recordMetadataDc;
  }

  public String getMetadataTagValueSeparatedBySpecialChar(List<String> tagValues) {

    String columnValue = "";
    for (String tagValue : tagValues) {
      columnValue += tagValue + CommonConstants.COLUMNVALUESEPARARTOR;
    }

    return columnValue;
  }

  public void setHarRepo(HarRepo harRepo) {
    this.harRepo = harRepo;
  }

  public void setMetadataPrefix(String metadataPrefix) {
    this.metadataPrefix = metadataPrefix;
  }
  
  
  
}
