/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.daoImpl;

import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOnly;
import in.gov.nvli.harvester.custom.annotation.TransactionalReadOrWrite;
import in.gov.nvli.harvester.dao.HarMetadataTypeDao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vootla
 */
@Repository
@TransactionalReadOnly
public class HarMetadataTypeDaoImpl extends GenericDaoImpl<HarMetadataType, Short> implements HarMetadataTypeDao {

    public HarMetadataTypeDaoImpl() {
        super(HarMetadataType.class);
    }

    @Override
    @TransactionalReadOrWrite
    public boolean saveHarMetadataTypes(List<HarMetadataType> metadataTypes) {
        try {
            for (HarMetadataType metadata : metadataTypes) {
                if (getMetadataTypeByMetadatPrefix(metadata.getMetadataPrefix()) != null) {
                    continue;
                }
                if (!createNew(metadata)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public HarMetadataType getMetadataTypeByMetadatPrefix(String metaPrefix) {

        HarMetadataType metadataType = null;
        try {
            metadataType = (HarMetadataType) currentSession().createCriteria(HarMetadataType.class).add(Restrictions.eq("metadataPrefix", metaPrefix)).uniqueResult();
        } catch (Exception e) {
        }
        return metadataType;
    }
}
