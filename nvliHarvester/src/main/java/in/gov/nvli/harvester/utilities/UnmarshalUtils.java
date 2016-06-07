/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.OAIPMH_beans.OAIPMHtype;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public class UnmarshalUtils {

    private static final String PATTERN_CODE = "[^\\u0009\\u000A\\u000D\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+";
    private static final String REPLACE_CODE = "";

    public static OAIPMHtype xmlToOaipmh(String response) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(OAIPMHtype.class);
        InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        JAXBElement data;
        OAIPMHtype element;
        try {
            data = (JAXBElement) context.createUnmarshaller().unmarshal(stream);
            element = OAIPMHtype.class.cast(data.getValue());
        } catch (JAXBException e) {
            Pattern p = Pattern.compile(PATTERN_CODE);
            String filteredData = p.matcher(response).replaceAll(REPLACE_CODE);
            
            stream = new ByteArrayInputStream(filteredData.getBytes(StandardCharsets.UTF_8));
            data = (JAXBElement) context.createUnmarshaller().unmarshal(stream);
            element = OAIPMHtype.class.cast(data.getValue());
        }
        return element;

    }

}
