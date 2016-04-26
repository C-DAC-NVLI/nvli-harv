/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import javax.servlet.http.HttpSession;

/**
 *
 * @author richa
 */
public interface ListRecordsService {

  public void getListRecord(String baseUrl);

  public void setHarRepo(HarRepo harRepo);

  public void setMetadataPrefix(String metadataPrefix);
  
  public void setSession(HttpSession session);
}
