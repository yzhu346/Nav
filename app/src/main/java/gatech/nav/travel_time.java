package gatech.nav; /**
 * Created by koamehare on 4/15/17.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class travel_time {


    public static int bustravel(String route_id, BusStop bussget_on, BusStop bussget_off, Location start_point) throws Exception {
        final String main = "https://gtbuses.herokuapp.com/multiPredictions?stops=";
        int travel_time=0;
        String path = main + route_id + "|" + bussget_on.getStopid();
        String path1 = main + route_id + "|" + bussget_off.getStopid();
        URL url = new URL(path);

        URLConnection connection = url.openConnection();
        Document doc = parseXML(connection.getInputStream());
        NodeList mlist = doc.getElementsByTagName("prediction");

        for (int temp1 = 0; temp1 < mlist.getLength(); temp1++) {
            int wait_time = 0;
            Node mNode = mlist.item(temp1);
            if (mNode.getNodeType() == Node.ELEMENT_NODE) {
                Element mElement = (Element) mNode;
                wait_time = Integer.parseInt(mElement.getAttribute("seconds"));
                int walktime = walk(start_point, bussget_on.getLocation());
                //System.out.println(walktime);
                //System.out.println(wait_time);
                if (wait_time > walktime) {
                    URL url1 = new URL(path1);
                    URLConnection connection1 = url1.openConnection();
                    Document doc1 = parseXML(connection1.getInputStream());
                    NodeList nList1 = doc1.getElementsByTagName("prediction");
                    for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                        Node nNode1 = nList1.item(temp2);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element nElement1 = (Element) nNode1;
                            if (nElement1.getAttribute("vehicle").equals(mElement.getAttribute("vehicle"))) {
                                String arrive_time = nElement1.getAttribute("seconds");
                                //System.out.println(arrive_time);
                                if (Integer.parseInt(arrive_time) > wait_time) {
                                    travel_time = Integer.parseInt(arrive_time) - wait_time;
                                    //System.out.println(travel_time);
                                    break;
                                }

                            }

                        }

                    }

                    break;
                }

            }
        }
        //System.out.println("the bus takes: " + travel_time);
        return travel_time;
    }

    public static int walk(Location start_point, Location end_point) throws Exception {
        String url1 = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=";
        String url2 = "&destinations=";
        String url3 = "&departure_time=now&traffic_model=best_guess&mode=walking&key=AIzaSyDfdmZLnPimkOIVckgLyJLtLRmXE5dEKhg";
        String path = url1 + start_point.getLatitude() + "," + start_point.getLongitude()+ url2 + end_point.getLatitude() + "," + end_point.getLongitude()+ url3;
        //System.out.println(path);
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
        //System.out.println("the walking takes: " + walk_time);
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