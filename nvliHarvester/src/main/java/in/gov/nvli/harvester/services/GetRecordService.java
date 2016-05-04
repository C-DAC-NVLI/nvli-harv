/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.RecordType;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.customised.MethodEnum;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public interface GetRecordService {

    public boolean saveGetRecord(String baseURL, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public boolean saveGetRecord(HarRepo repository, MethodEnum method, String adminEmail, String identifier, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public boolean saveGetRecordList(List<RecordType> recordTypeList, HarRepo repository, String metadataPrefix) throws MalformedURLException, IOException, JAXBException, ParseException;

    public HarRecordMetadataDc convertOAIDCToHarRecordMetadataDc(OAIDC oaiDC);

}
