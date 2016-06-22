/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.dao;

import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.HarRepo;
import java.util.List;

/**
 *
 * @author richa
 */
public interface HarRecordDao extends GenericDao<HarRecord, Long> {

    public void saveOrUpdateHarRecordList(List<HarRecord> records);

    public void saveOrUpdateHarRecord(HarRecord harRecordObj);

    public HarRecord getHarRecordByRecordIdentifier(String recordIdentifier);

    public long getCount(HarRepo harRepoObj);

    public HarRecord getRecord(long recordId);

    public Long rowCount(short recordStaus);

    public List<HarRecord> list(int displayStart, int displayLength, short recordStatus);

}
