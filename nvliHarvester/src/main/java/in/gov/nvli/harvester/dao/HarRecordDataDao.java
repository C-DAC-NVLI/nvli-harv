/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRecordData;
import in.gov.nvli.harvester.beans.HarRepo;
import java.util.List;

/**
 *
 * @author ankit
 */
public interface HarRecordDataDao extends GenericDao<HarRecordData, Long> {

    public List<HarRecordData> list(HarRepo harRepoObject);

    public void saveOrUpdateHarRecordDataList(List<HarRecordData> harRecordDataList);

    public HarRecordData get(HarRecord harRecordobj);
}
