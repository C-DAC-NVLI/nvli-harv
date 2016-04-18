/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarSet;

/**
 *
 * @author vootla
 */
public class OAIBeanConverter {
    
    public static HarSet setTypeToHarSet(SetType setType)
    {
        HarSet obj=new HarSet(setType.getSetName(),setType.getSetSpec());
      if(setType.getSetDescription()!=null&&setType.getSetDescription().size()!=0)
      {
          //we have to parse discription an dwe hav eto store as it is
      }
       return obj;
    }
    
    public static HarMetadataType metadataFormatTypeToHarMetadataType(MetadataFormatType metadataFormatType)
    {
        HarMetadataType obj=new HarMetadataType(metadataFormatType.getMetadataPrefix(), metadataFormatType.getSchema(), metadataFormatType.getMetadataNamespace());
      
       return obj;
    }

}
