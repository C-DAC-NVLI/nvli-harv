/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.services;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.custom.harvester_enum.MethodEnum;
import in.gov.nvli.harvester.customised.HarRepoCustomised;
import java.util.List;

/**
 *
 * @author ankit
 */
public interface RepositoryMetadataService {
    public boolean saveRepositoryMetadata(HarRepoCustomised harRepoCustomisedObj, MethodEnum method, String adminEmail);
    
    public boolean changeRepositoryMetadataStatus(HarRepo harRepoObj, short statusId);
    
    public void saveOrUpdateRepositoryMetadata(HarRepoCustomised harRepoCustomisedObj, HarRepo harRepoObj);
    
    public List<HarRepoMetadata> list(HarRepo harRepoObj);
}
