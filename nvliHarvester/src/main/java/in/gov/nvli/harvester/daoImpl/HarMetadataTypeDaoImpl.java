/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vootla
 */
@Repository
public class HarMetadataTypeDaoImpl extends GenericDaoImpl<HarMetadataType, Short> implements HarMetadataTypeDao {

 

  @Override
  public HarMetadataType getMetadataType(Short metadataId) {
 
      HarMetadataType metadataType=null;
    try {
    metadataType = get(metadataId);
    } catch (Exception e) {
    }
    return metadataType;
  }

    @Override
    public boolean saveHarMetadataTypes(List<HarMetadataType> metadataTypes) {
   try
       {
        for(HarMetadataType metadata:metadataTypes)
        {
           if(!createNew(metadata))
               return false;
        }
      return true;
       }catch(Exception e)
       {
           return false;
       }
    }
    

}
