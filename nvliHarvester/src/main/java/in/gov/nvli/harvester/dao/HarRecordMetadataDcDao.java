/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordMetadataDc;
import java.util.List;

/**
 *
 * @author richa
 */
public interface HarRecordMetadataDcDao extends GenericDao<HarRecordMetadataDc, Long> {

    public boolean saveOrUpdateHarRecordMetadataDcList(List<HarRecordMetadataDc> metadataDcs);

    public HarRecordMetadataDc GetByHarRecord(HarRecord harRecord);
}
