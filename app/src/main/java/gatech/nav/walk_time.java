package gatech.nav;//import com.google.gson.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.lang.String;


public class walk_time {


    public static int walk(String origins_lat, String origins_lon, String destination_lat, String destination_lon) throws Exception {
        String url1 = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=";
        String url2 = "&destinations=";
        String url3 = "&departure_time=now&traffic_model=best_guess&mode=walking&key=AIzaSyDfdmZLnPimkOIVckgLyJLtLRmXE5dEKhg";
        String path = url1 + origins_lat + "," + origins_lon + url2 + destination_lat + "," + destination_lon + url3;
        System.out.println(path);
        URL url = new URL(path);
        URLConnection connection = url.openConnection();
        Document doc = parseXML(connection.getInputStream());
        NodeList nList = doc.getElementsByTagName("distance");
        Node nNode = nList.item(0);
        int walk_time=0;
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            NodeList text = eElement.getElementsByTagName("text");
            Element textElm = (Element) text.item(0);
            Node textNode = textElm.getChildNodes().item(0);
            //System.out.println("distance : " + textNode.getNodeValue());
            NodeList mList = doc.getElementsByTagName("duration");
            Node mNode = mList.item(0);
            if (mNode.getNodeType() == Node.ELEMENT_NODE) {
                Element mElement = (Element) mNode;
                NodeList text1 = mElement.getElementsByTagName("text");
                Element textElm1 = (Element) text1.item(0);
                Node textNode1 = textElm1.getChildNodes().item(0);
                //System.out.println("duration : " + textNode1.getNodeValue());
                String[] group = textNode1.getNodeValue().split(" ");
                //System.out.println(Integer.parseInt(group[0]));
                walk_time = Integer.parseInt(group[0])*60;
            }

        }
        System.out.println("the walking takes: " + walk_time);
        return walk_time;
    }
    private static Document parseXML(InputStream stream)
        throws Exception {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        } catch (Exception ex) {
            throw ex;
        }

            return doc;
        }
}
