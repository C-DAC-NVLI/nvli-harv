/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarSet;
import in.gov.nvli.harvester.dao.HarSetDao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vootla
 */
@Repository
@Transactional(readOnly = true)
public class HarSetDaoImpl extends GenericDaoImpl<HarSet, Long> implements HarSetDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HarSetDaoImpl.class);

    public HarSetDaoImpl() {
        super(HarSet.class);
    }

    @Override
    @Transactional
    public boolean saveHarSets(List<HarSet> sets) {
        try {
            for (HarSet set : sets) {
               if(getHarSetType(set.getName(),set.getSetSpec())!=null)
                   continue;
                if (!createNew(set)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public HarSet getHarSet(String set) {

        HarSet harSet = null;
        try {
            harSet = (HarSet) currentSession().createCriteria(HarSet.class).add(Restrictions.eq("name", set)).uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return harSet;
    }

    @Override
    public HarSet getHarSetType(String name, String setSpec) {
        HarSet harSet = null;
        try {
            harSet = (HarSet) currentSession().createCriteria(HarSet.class).add(Restrictions.and(Restrictions.eq("name", name), Restrictions.eq("setSpec", setSpec))).uniqueResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return harSet;
    }
}
