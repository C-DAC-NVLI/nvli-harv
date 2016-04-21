/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarSet;
import java.util.List;

/**
 *
 * @author vootla
 */
public interface HarSetDao extends GenericDao<HarSet, Long>{
    
    public boolean saveHarSets(List<HarSet> sets);
    public HarSet getHarSet(String set);
    public HarSet getHarSetType(String name,String setSpec);
    
}
