/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRepoMetadata;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import java.util.Date;

/**
 *
 * @author ankit
 */
public interface HarRepoMetadataDao extends GenericDao<HarRepoMetadata, Integer> {

    public HarRepoMetadata get(int harRepoId, HarRecordMetadataType harRecordMetadataTypeObj);

    public boolean updateStatus(HarRepoMetadata harRepoMetadataObj, short status);

    public void updateStartTime(HarRepoMetadata harRepoMetadataObj, Date updatedDate);

    public void updateEndTime(HarRepoMetadata harRepoMetadataObj, Date updatedDate);
}
