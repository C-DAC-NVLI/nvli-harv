/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.IdentifyType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
//@Service
public interface IdentifyService {

    public HarRepo getIdentify(String baseURL, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException;

    public IdentifyType getIdentifyTypeObject(String baseURL, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException;

    public HarRepo convertIdentifyTypeToHarRepo(IdentifyType identifyTypeObject);

}
