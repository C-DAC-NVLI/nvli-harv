/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.customised.MethodEnum;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
public interface ListSetsService {

    public boolean saveHarSets(String baseUrl, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException;

    public HarSet getHarSetByNameAndSpec(String name, String Spec);

    public boolean saveOrUpdateHarSets(String baseUrl, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException;
}
