/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.custom.exception.OAIPMHerrorTypeException;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import java.io.IOException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author vootla
 */
public interface ListSetsService {

    public boolean saveHarSets(String baseURL, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException;
    
    public boolean saveHarSets(HarRepo repository, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException;

    public HarSet getHarSetByNameAndSpec(String name, String Spec);

    public boolean saveOrUpdateHarSets(HarRepo repository, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException;
    
    public boolean saveOrUpdateHarSets(String baseURL, MethodEnum method, String adminEmail) throws IOException, JAXBException, OAIPMHerrorTypeException;
}
