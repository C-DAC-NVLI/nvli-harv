/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.servicesImpl;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.dao.HarSetDao;
import in.gov.nvli.harvester.services.ListSetsService;
import in.gov.nvli.harvester.utilities.HttpURLConnectionUtil;
import in.gov.nvli.harvester.utilities.OAIBeanConverter;
import in.gov.nvli.harvester.utilities.OAIResponseUtil;
import in.gov.nvli.harvester.utilities.UnmarshalUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vootla
 */
@Service
public class ListSetsServiceImpl implements ListSetsService {

    @Autowired
    public HarSetDao harSetDao;
    private HttpURLConnection connection;

    @Override
    public int getConnectionStatus(String baseURL, String method, String userAgnet, String adminEmail) throws MalformedURLException, IOException {
        connection = HttpURLConnectionUtil.getConnection(baseURL, "GET", "", "");
        int status = HttpURLConnectionUtil.getConnectionStatus(connection);
        if (status == -1) {
            connection.disconnect();
        }
        return status;
    }

    @Override
    public List<SetType> getListSets() throws IOException, JAXBException {
        String response = OAIResponseUtil.createResponseFromXML(connection);
        OAIPMHtype obj = (OAIPMHtype) UnmarshalUtils.xmlToOaipmh(response);
        return obj.getListSets().getSet();
    }

    @Override
    public boolean saveListSets() throws IOException, JAXBException {
        List<HarSet> sets = new ArrayList<>();
        for (SetType setTemp : getListSets()) {
            sets.add(OAIBeanConverter.setTypeToHarSet(setTemp));
        }
        return harSetDao.saveHarSets(sets);
    }

    @Override
    public List<SetType> getListSets(String baseUrl) throws MalformedURLException, IOException, JAXBException {
        connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");

        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return null;
        } else {
            return getListSets();
        }

    }

    @Override
    public boolean saveListSets(String baseUrl) throws MalformedURLException, IOException, JAXBException {
        connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return false;
        } else {
            return saveListSets();
        }
    }

    @Override
    public HarSet getHarSetType(String name, String setSpec) {
        return harSetDao.getHarSet(name, setSpec);
    }

    @Override
    public boolean saveOrUpdateListSets(String baseUrl) throws MalformedURLException, IOException, JAXBException {
        connection = HttpURLConnectionUtil.getConnection(baseUrl, "GET", "", "");
        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return false;
        }else{
            return saveOrUpdateListSets();
        }
    }
    
    private boolean saveOrUpdateListSets() throws IOException, JAXBException {
        List<HarSet> sets = new ArrayList<>();
        for (SetType setTemp : getListSets()) {
            sets.add(OAIBeanConverter.setTypeToHarSet(setTemp));
        }
        return harSetDao.saveOrUpdateHarSets(sets);
    }
}
