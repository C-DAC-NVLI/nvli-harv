/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.beans.HarRepo;
import in.gov.nvli.harvester.customised.HarRepoCustomised;

/**
 *
 * @author vootla
 */
public class CustomBeansGenerator {
  
    public static HarRepoCustomised convertHarRepoToHarRepoCustomised(HarRepo harRepo)
    {
        HarRepoCustomised custObj=new HarRepoCustomised();
        custObj.setRepoId(harRepo.getRepoId());
        custObj.setRepoBaseUrl(harRepo.getRepoBaseUrl());
        custObj.setRepoName(harRepo.getRepoName());
        custObj.setRepoLastSyncDate(harRepo.getRepoLastSyncDate());
        return  custObj;
    }
     public static HarRepo convertHarRepoCustomisedToHarRepo(HarRepoCustomised harRepoCustomised)
    {
        HarRepo harRepoObj=new HarRepo();
        harRepoObj.setRepoId(harRepoCustomised.getRepoId());
        harRepoObj.setRepoBaseUrl(harRepoCustomised.getRepoBaseUrl());
        harRepoObj.setRepoName(harRepoCustomised.getRepoName());
        harRepoObj.setRepoLastSyncDate(harRepoCustomised.getRepoLastSyncDate());
        return  harRepoObj;
    }
}
