/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.dao.HarSetDao;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vootla
 */
@Repository
public class HarSetDaoImpl  extends GenericDaoImpl<HarSet,Long> implements HarSetDao{

    @Override
    public boolean saveHarSets(List<HarSet> sets) {
       try
       {
        for(HarSet set:sets)
        {
           if(!createNew(set))
               return false;
        }
      return true;
       }catch(Exception e)
       {
           return false;
       }
    }
    
}
