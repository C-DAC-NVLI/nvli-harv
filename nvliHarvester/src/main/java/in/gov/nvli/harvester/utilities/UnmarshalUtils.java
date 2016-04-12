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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 *
 * @author richa
 */
public class UnmarshalUtils {

 

  public static OAIPMHtype xmlToOaipmh(String response) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(OAIPMHtype.class);
    InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
    JAXBElement data = (JAXBElement) context.createUnmarshaller().unmarshal(stream);
    OAIPMHtype element = OAIPMHtype.class.cast(data.getValue());

    return element;
  }

}
