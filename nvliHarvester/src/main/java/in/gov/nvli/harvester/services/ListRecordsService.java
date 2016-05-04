/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.MethodEnum;
import javax.servlet.ServletContext;

/**
 *
 * @author richa
 */
public interface ListRecordsService {

    public boolean saveListRecords(HarRepo harRepoObj, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag);

    public boolean saveListRecords(String baseURL, String metadataPrefix, MethodEnum method, String adminEmail, boolean incrementalFlag);

    public void setServletContext(ServletContext servletContext);

}
