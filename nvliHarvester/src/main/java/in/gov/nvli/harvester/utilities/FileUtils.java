/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.utilities;

import in.gov.nvli.harvester.customised.HarRecordDataCustomised;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ankit
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static void saveFile(String url, String filePath, String fileName) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fos = null;
        File targetDirectory;

        try {
            HttpURLConnection con = HttpURLConnectionUtil.getConnection(url);
            if (HttpURLConnectionUtil.isConnectionAlive(con)) {
                in = new BufferedInputStream(con.getInputStream());
                out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }

                byte[] response = out.toByteArray();

                targetDirectory = new File(filePath);
                targetDirectory.mkdirs();

                fos = new FileOutputStream(filePath + File.separator + fileName);
                fos.write(response);
                LOGGER.info("File saved : " + filePath + File.separator + fileName);
            }
        } catch (IOException e) {
            LOGGER.error("\nActivity --> Downloading ORE Data"
                    + "\nCallingURL --> " + url
                    + "\nErrorCode --> " + e.getMessage(), e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }

    public static String getNameFromURL(String url) {
        if (url != null) {
            if (url.contains("?")) {
                return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
            } else {
                return url.substring(url.lastIndexOf("/") + 1);
            }
        }
        return null;
    }

    public static void saveFile(HarRecordDataCustomised harRecordDataCustomisedObj, String filePath) throws IOException {
        String fileName;
        if (harRecordDataCustomisedObj.getFileName() == null || harRecordDataCustomisedObj.getFileName().isEmpty()) {
            fileName = getNameFromURL(harRecordDataCustomisedObj.getFileURL());
        } else {
            fileName = harRecordDataCustomisedObj.getFileName();
        }
        saveFile(harRecordDataCustomisedObj.getFileURL(), filePath, fileName);
    }

    public static void main(String[] args) throws IOException {
//        saveFile("http://dspace.library.iitb.ac.in/jspui/bitstream/handle/100/5/license.txt?sequence=2", "/home/ankit/.harvester", "APSYM2004.pdf");
//        System.err.println("Saved");
        System.err.println("Value is " + getNameFromURL("http://localhost:8080/xmlui/bitstream/1849/8/1/license.txt"));
        URL url = new URL("http://localhost/xmlui/bitstream/1849/8/1/license.txt");
        String baseUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
        System.out.println("URL is " + baseUrl);
    }

}
