/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarMetadataType;
import java.util.List;

/**
 *
 * @author vootla
 */
public interface HarMetadataTypeDao extends GenericDao<HarMetadataType, Short> {

    public boolean saveHarMetadataTypes(List<HarMetadataType> metadataTypes);

    public HarMetadataType getMetadataTypeByMetadatPrefix(String metaPrefix);
}
