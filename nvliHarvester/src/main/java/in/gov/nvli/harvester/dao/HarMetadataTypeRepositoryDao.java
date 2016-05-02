/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarMetadataTypeRepository;
import in.gov.nvli.harvester.beans.HarRepo;
import java.util.List;

/**
 *
 * @author vootla
 */
public interface HarMetadataTypeRepositoryDao extends GenericDao<HarMetadataTypeRepository, Short> {

    public boolean saveHarMetadataTypesOfRepository(List<HarMetadataTypeRepository> metadataTypesOfRepo);

    public HarMetadataTypeRepository getMetadataTypeRepository(HarMetadataType metadataType, HarRepo repository);
}
