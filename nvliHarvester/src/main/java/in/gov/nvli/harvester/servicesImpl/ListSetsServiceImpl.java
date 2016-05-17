/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.OAIPMH_beans.VerbType;
import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.customised.MethodEnum;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.dao.RepositoryDao;
import in.gov.nvli.harvester.services.ListSetsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIBeanConverter;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class ListSetsServiceImpl implements ListSetsService {

    @Autowired
    private HarSetDao harSetDao;

    @Autowired
    private RepositoryDao repositoryDao;    
    
    private List<SetType> getSetTypeList(HttpURLConnection connection) throws IOException, JAXBException {
        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);
        return obj.getListSets().getSet();
    }
    
    @Override
    public boolean saveHarSets(String baseURL, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        HarRepo repository = repositoryDao.getRepository(baseURL);
        return saveHarSets(repository, method, adminEmail);
    }

    @Override
    public boolean saveHarSets(HarRepo repository, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        String desiredURL = repository.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_SETS.value();
        return saveOrUpdate(desiredURL, method, adminEmail, false);
    }
    
    private boolean saveOrUpdate(String baseUrl, MethodEnum method, String adminEmail, boolean updateFlag) throws MalformedURLException, IOException, JAXBException{
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(baseUrl, method, adminEmail);
        if (!HttpURLConnectionUtil.isConnectionAlive(connection)) {
            connection.disconnect();
            return false;
        } else {
            List<HarSet> sets = OAIBeanConverter.convertSetTypeToHarSet(getSetTypeList(connection));
            connection.disconnect();
            if(updateFlag){
                return harSetDao.saveOrUpdateHarSets(sets);
            }else{
                return harSetDao.saveHarSets(sets);
            }
            
        }
    }
    
    @Override
    public HarSet getHarSetByNameAndSpec(String name, String Spec) {
        return harSetDao.getHarSet(name, Spec);
    }

    @Override
    public boolean saveOrUpdateHarSets(HarRepo repository, MethodEnum method, String adminEmail) throws MalformedURLException, IOException, JAXBException {
        String desiredURL = repository.getRepoBaseUrl() + CommonConstants.VERB + VerbType.LIST_SETS.value();
        return saveOrUpdate(desiredURL, method, adminEmail, true);
    }
}
