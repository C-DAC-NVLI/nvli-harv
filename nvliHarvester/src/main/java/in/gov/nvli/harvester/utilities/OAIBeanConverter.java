/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataFormatType;
import in.gov.nvli.harvester.OAIPMH_beans.SetType;
import in.gov.nvli.harvester.beans.HarMetadataType;
import in.gov.nvli.harvester.beans.HarSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vootla
 */
public class OAIBeanConverter {

    public static HarSet convertSetTypeToHarSet(SetType setType) {
        HarSet obj = new HarSet(setType.getSetName(), setType.getSetSpec());
        if (setType.getSetDescription() != null && setType.getSetDescription().size() != 0) {
            //we have to parse discription an dwe hav eto store as it is
        }
        return obj;
    }

    public static List<HarSet> convertSetTypeToHarSet(List<SetType> setTypeList) {
        List<HarSet> harSetList = new ArrayList<>();
        HarSet harSetObject;
        for (SetType tempSetType : setTypeList) {
            harSetObject = new HarSet(tempSetType.getSetName(), tempSetType.getSetSpec());
            harSetList.add(harSetObject);
        }
        return harSetList;
    }

    public static HarMetadataType convertMetadataFormatTypeToHarMetadataType(MetadataFormatType metadataFormatType) {
        return new HarMetadataType(metadataFormatType.getMetadataPrefix(), metadataFormatType.getSchema(), metadataFormatType.getMetadataNamespace());

    }

    public static List<HarMetadataType> convertMetadataFormatTypeToHarMetadataType(List<MetadataFormatType> metadataFormatTypeList) {
        List<HarMetadataType> harMetadataTypeList = new ArrayList<>();
        HarMetadataType harMetadataTypeObject;
        for (MetadataFormatType tempMetadataFormatType : metadataFormatTypeList) {
            harMetadataTypeObject = new HarMetadataType(tempMetadataFormatType.getMetadataPrefix(), tempMetadataFormatType.getSchema(), tempMetadataFormatType.getMetadataNamespace());
            harMetadataTypeList.add(harMetadataTypeObject);
        }
        return harMetadataTypeList;

    }

}
