/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.OAIPMH_beans.MetadataType;
import in.gov.nvli.harvester.beans.HarRecord;
import in.gov.nvli.harvester.beans.OAIDC;
import in.gov.nvli.harvester.constants.CommonConstants;
import in.gov.nvli.harvester.custom.harvester_enum.HarRecordMetadataType;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author ankit
 */
public class MarshalUtils {

    
    private static final String FILE_EXTENSION = ".xml";

    public static boolean oaipmhToXML(MetadataType metadataTypeObj, HarRecord harRecord, HarRecordMetadataType harRecordMetadataType, String baseFilePath) throws JAXBException, IOException, TransformerException {
        Class targetClass = null;
        String targetDirectory = null;
        Object targetObject = null;
        Document document;

        if (null != harRecordMetadataType) {
            switch (harRecordMetadataType) {
                case METS:
//                  targetClass = Mets.class;
                    targetDirectory = CommonConstants.DIRECTORY_Name_METS;
//                  targetObject = metadataTypeObj.getMets();
                    break;
                case MARC:
//                  targetClass = RecordType.class;
                    targetDirectory = CommonConstants.DIRECTORY_Name_MARC;
//                  targetObject = metadataTypeObj.getMarc();
                    break;
                case ORE:
//                  targetClass = EntryType.class;
                    targetDirectory = CommonConstants.DIRECTORY_Name_ORE;
//                  targetObject = metadataTypeObj.getAtom();
                    break;
                case OAI_DC:
//                  targetClass = EntryType.class;
                    targetDirectory = CommonConstants.DIRECTORY_Name_OAI_DC;
//                  targetObject = metadataTypeObj.getAtom();
                    break;
                default:
                    break;
            }
        }

//      JAXBContext context = JAXBContext.newInstance(targetClass);
        File xmlFile = new File(baseFilePath + File.separator + harRecord.getRepoId().getRepoUID() + File.separator + harRecord.getRecordUid() + File.separator + targetDirectory + File.separator + harRecord.getRecordUid() + FILE_EXTENSION);
        xmlFile.getParentFile().mkdirs();
        xmlFile.createNewFile();

//        OutputStream out = new FileOutputStream(xmlFile);
//        Marshaller m = context.createMarshaller();
//        m.marshal(targetObject, out);
        if (harRecordMetadataType == HarRecordMetadataType.OAI_DC) {
            DOMResult res = new DOMResult();
            JAXBContext context = JAXBContext.newInstance(OAIDC.class);
            context.createMarshaller().marshal(metadataTypeObj.getOaidc(), res);
            document = (Document) res.getNode();
        } else {
            Element elementObj = (Element) metadataTypeObj.getAny();
            document = elementObj.getOwnerDocument();
        }

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlFile);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.transform(source, result);

        return true;

    }
}
