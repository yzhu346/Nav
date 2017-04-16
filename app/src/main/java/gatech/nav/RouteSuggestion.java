package gatech.nav; /**
 * Created by xinyao on 4/4/17.
 */
//package com.mkyong.csv;
/*import com.sun.corba.se.impl.encoding.CodeSetConversion;*/

import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;


public class RouteSuggestion {

    private static String[] route = {"blue", "green", "red", "express", "trolley"};

    public static class BusStopComparator implements Comparator<BusStop> {

        @Override
        public int compare(BusStop x, BusStop y) {

            double dist_x = (Math.pow(Double.parseDouble(x.getLocation().getLatitude()), 2) + Math.pow(Double.parseDouble(x.getLocation().getLongitude()), 2));
            double dist_y = (Math.pow(Double.parseDouble(y.getLocation().getLatitude()), 2) + Math.pow(Double.parseDouble(y.getLocation().getLongitude()), 2));

            if (dist_x < dist_y) {
                return -1;
            } else if (dist_y < dist_x) {
                return 1;
            }
            return 0;
        }
    }

    public static ArrayList<BusStop> nearestStops (Location curr, ArrayList<BusStop> busStops) {

        ArrayList<BusStop> relativeStop = new ArrayList<>();
        ArrayList<BusStop> ret = new ArrayList<>();
        HashMap<BusStop, Integer> busstop_index = new HashMap<>();

        int num_stops = busStops.size();
        int index = 0;

        // get the relative location for each busStop to the current location
        for (BusStop busstop : busStops) {
            double relative_lat = Double.parseDouble(busstop.getLocation().getLatitude()) - Double.parseDouble(curr.getLatitude());
            double relative_lon = Double.parseDouble(busstop.getLocation().getLongitude()) - Double.parseDouble(curr.getLongitude());
            Location relative_loc = new Location(Double.toString(relative_lat), Double.toString(relative_lon));
            BusStop relative_busstop = new BusStop(busstop.getStopid(), relative_loc);
            relativeStop.add(relative_busstop);
            busstop_index.put(relative_busstop, index);
            index++;
        }

        Comparator<BusStop> comparator = new BusStopComparator();

        // use priority queue to store all the relative busstops
        PriorityQueue<BusStop> pq = new PriorityQueue<>(num_stops, comparator);
        for (BusStop busstop : relativeStop) {
            pq.add(busstop);
        }

        // find the two nearest bus stops tp the current location

        for (int i = 0 ; i < 4; i++) {
            if (!pq.isEmpty()) {
                BusStop temp_relative = pq.poll();
                ret.add(busStops.get(busstop_index.get(temp_relative)));
            }
        }
        return ret;
    }

    public static ArrayList<Path> routeOption  (Location user, Location dest, ArrayList<BusStop> busStops, HashMap<String, ArrayList<Integer>> bus_route) {

        // fine two nearest busstops for the user and the destination
        ArrayList<BusStop> neareststops_user = nearestStops(user, busStops);
        ArrayList<BusStop> neareststops_dest = nearestStops(dest, busStops);

        // parse the user and destination's locations to String
        String user_lat = user.getLatitude();
        String user_lon = user.getLongitude();
        String dest_lat = dest.getLatitude();
        String dest_lon = dest.getLongitude();

        // the return String
        ArrayList<Path> ret = new ArrayList<>();
        //suggest the route
        for (BusStop stop_user : neareststops_user) {
            for (BusStop stop_dest : neareststops_dest) {
                for (int routeId_user : bus_route.get(stop_user.getStopid())) {
                    for (int routeId_stop : bus_route.get(stop_dest.getStopid())) {
                        if (routeId_user == routeId_stop) {
                            //System.out.println("One option: " + stop_user.getStopid() + " to "
                                    //+ stop_dest.getStopid() + " via " + route[routeId_stop] + " route");
                            try {
                                int totalTime = 0;

                                String getonStop_lat = stop_user.getLocation().getLatitude();
                                String getonStop_lon = stop_user.getLocation().getLongitude();
                                String getoffStop_lat = stop_dest.getLocation().getLatitude();
                                String getoffStop_lon = stop_dest.getLocation().getLongitude();

                                if (travel_time.travel(route[routeId_stop], stop_user, stop_dest, user) == 0) continue;

                                totalTime = travel_time.travel(route[routeId_stop], stop_user, stop_dest, user)
                                        + walk_time.walk(user_lat, user_lon, getonStop_lat, getonStop_lon)
                                        + walk_time.walk(getoffStop_lat, getoffStop_lon, dest_lat, dest_lon);

                                System.out.println("total time: " + totalTime);
                                ret.add(new Path(stop_user.getStopid(), stop_dest.getStopid(), route[routeId_user], totalTime));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
    public static ArrayList<String> Solution (Location user, Location dest) {

        ArrayList<BusStop> busStops = new ArrayList<BusStop>();
        HashMap<String, Integer> BusRouteId = new HashMap<>();
        HashMap<String, ArrayList<Integer>> BusStop_Route = new HashMap<>();

        BusRouteId.put("blue", 0);
        BusRouteId.put("green", 1);
        BusRouteId.put("red", 2);
        BusRouteId.put("express", 3);
        BusRouteId.put("trolley", 4);

        ArrayList<BusStop> TotalBusStops_GT = new ArrayList<>();
        {
            //Express
            TotalBusStops_GT.add(new BusStop("cloucomm", new Location("33.7753", "-84.39611")));
            TotalBusStops_GT.add(new BusStop("techsqua_ob", new Location("33.7768", "-84.38975")));
            TotalBusStops_GT.add(new BusStop("duprmrt", new Location("33.77678", "-84.38749")));
            TotalBusStops_GT.add(new BusStop("techsqua", new Location("33.77692", "-84.38978")));

            //Trolley
            TotalBusStops_GT.add(new BusStop("marta_a", new Location("33.78082", "-84.38641")));
            TotalBusStops_GT.add(new BusStop("publix", new Location("33.7806", "-84.388818")));
            TotalBusStops_GT.add(new BusStop("techsqua", new Location("33.77692", "-84.38978")));
            TotalBusStops_GT.add(new BusStop("tech5rec", new Location("33.776896", "-84.391581")));
            TotalBusStops_GT.add(new BusStop("fersforec", new Location("33.776949", "-84.394234")));
            TotalBusStops_GT.add(new BusStop("ferschrec", new Location("33.777634", "-84.39575")));
            TotalBusStops_GT.add(new BusStop("fersatla", new Location("33.77832", "-84.398083")));
            TotalBusStops_GT.add(new BusStop("fersherec", new Location("33.778141", "-84.401829")));
            TotalBusStops_GT.add(new BusStop("recctr", new Location("33.7751", "-84.40265")));
            TotalBusStops_GT.add(new BusStop("studcentr", new Location("33.77335", "-84.39917")));
            TotalBusStops_GT.add(new BusStop("tranhub_a", new Location("33.773261", "-84.397058")));
            TotalBusStops_GT.add(new BusStop("centrstud", new Location("33.77335", "-84.39917")));
            TotalBusStops_GT.add(new BusStop("ferstdr", new Location("33.774997", "-84.402359")));
            TotalBusStops_GT.add(new BusStop("fershemrt", new Location("33.778363", "-84.401007")));
            TotalBusStops_GT.add(new BusStop("fersatl_ib", new Location("33.77819", "-84.397491")));
            TotalBusStops_GT.add(new BusStop("ferschmrt", new Location("33.777439", "-84.395508")));
            TotalBusStops_GT.add(new BusStop("fersfomrt", new Location("33.776925", "-84.393671")));
            TotalBusStops_GT.add(new BusStop("tech5mrt", new Location("33.776878", "-84.391914")));
            TotalBusStops_GT.add(new BusStop("techsqua_ib", new Location("33.7768", "-84.38975")));
            TotalBusStops_GT.add(new BusStop("duprmrt", new Location("33.77678", "-84.38749")));
            TotalBusStops_GT.add(new BusStop("wpe7mrt", new Location("33.778536", "-84.38724")));

            //Blue
            TotalBusStops_GT.add(new BusStop("fitthall_a", new Location("33.778274", "-84.404191")));
            TotalBusStops_GT.add(new BusStop("mcmil8th", new Location("33.779599", "-84.404164")));
            TotalBusStops_GT.add(new BusStop("8thhemp", new Location("33.779631", "-84.40274")));
            TotalBusStops_GT.add(new BusStop("reccent", new Location("33.7751", "-84.40265")));
            TotalBusStops_GT.add(new BusStop("studcentr", new Location("33.77335", "-84.39917")));
            TotalBusStops_GT.add(new BusStop("fershub", new Location("33.772842", "-84.397359")));
            TotalBusStops_GT.add(new BusStop("cherfers", new Location("33.77234", "-84.3957")));
            TotalBusStops_GT.add(new BusStop("naveapts_a", new Location("33.77019", "-84.39174")));
            TotalBusStops_GT.add(new BusStop("technorth", new Location("33.771857", "-84.39192")));
            TotalBusStops_GT.add(new BusStop("3rdtech", new Location("33.774061", "-84.39192")));
            TotalBusStops_GT.add(new BusStop("4thtech", new Location("33.775066", "-84.39194")));
            TotalBusStops_GT.add(new BusStop("5thtech", new Location("33.776401", "-84.392123")));
            TotalBusStops_GT.add(new BusStop("fersfowl", new Location("33.776949", "-84.394234")));
            TotalBusStops_GT.add(new BusStop("fersklau", new Location("33.777634", "-84.39575")));
            TotalBusStops_GT.add(new BusStop("fersatla", new Location("33.77832", "-84.398083")));
            TotalBusStops_GT.add(new BusStop("fersstat", new Location("33.778436", "-84.399961")));
            TotalBusStops_GT.add(new BusStop("fershemp_ob", new Location("33.778141", "-84.401829")));

            //Red
            TotalBusStops_GT.add(new BusStop("fitthall", new Location("33.778274", "-84.404191")));
            TotalBusStops_GT.add(new BusStop("mcmil8th", new Location("33.779599", "-84.404164")));
            TotalBusStops_GT.add(new BusStop("8thhemp", new Location("33.779631", "-84.40274")));
            TotalBusStops_GT.add(new BusStop("fershemp", new Location("33.778363", "-84.401007")));
            TotalBusStops_GT.add(new BusStop("fersstat", new Location("33.778293", "-84.399279")));
            TotalBusStops_GT.add(new BusStop("fersatla", new Location("33.77819", "-84.397491")));
            TotalBusStops_GT.add(new BusStop("klaubldg", new Location("33.777439", "-84.395508")));
            TotalBusStops_GT.add(new BusStop("fersfowl", new Location("33.776925", "-84.393671")));
            TotalBusStops_GT.add(new BusStop("tech5th", new Location("33.776401", "-84.392123")));
            TotalBusStops_GT.add(new BusStop("tech4th", new Location("33.774954", "-84.392049")));
            TotalBusStops_GT.add(new BusStop("techbob", new Location("33.773667", "-84.39205")));
            TotalBusStops_GT.add(new BusStop("technorth", new Location("33.77145", "-84.3921")));
            TotalBusStops_GT.add(new BusStop("naveapts_a", new Location("33.76994", "-84.391629")));
            TotalBusStops_GT.add(new BusStop("ferstcher", new Location("33.772284", "-84.39548")));
            TotalBusStops_GT.add(new BusStop("centrstud", new Location("33.77346", "-84.399159")));
            TotalBusStops_GT.add(new BusStop("hubfers", new Location("33.77276", "-84.396983")));
            TotalBusStops_GT.add(new BusStop("creccent", new Location("33.774997", "-84.402359")));

            //Green
            TotalBusStops_GT.add(new BusStop("tranhub", new Location("33.773226", "-84.397016")));
            TotalBusStops_GT.add(new BusStop("studcent_ib", new Location("33.77335", "-84.39917")));
            TotalBusStops_GT.add(new BusStop("creccent", new Location("33.774997", "-84.402359")));
            TotalBusStops_GT.add(new BusStop("fershemp", new Location("33.778363", "-84.401007")));
            TotalBusStops_GT.add(new BusStop("fersstat", new Location("33.778293", "-84.399279")));
            TotalBusStops_GT.add(new BusStop("ndec", new Location("33.780233", "-84.39905")));
            TotalBusStops_GT.add(new BusStop("10thhemp", new Location("33.781619", "-84.404082")));
            TotalBusStops_GT.add(new BusStop("hempcurr", new Location("33.784665", "-84.405937")));
            TotalBusStops_GT.add(new BusStop("14thbusy_a", new Location("33.78617", "-84.405241")));
            TotalBusStops_GT.add(new BusStop("14thstat", new Location("33.78613", "-84.398799")));
            TotalBusStops_GT.add(new BusStop("gcat", new Location("33.78628", "-84.39559")));
            TotalBusStops_GT.add(new BusStop("glc", new Location("33.78158", "-84.39645")));
            TotalBusStops_GT.add(new BusStop("bakebldg", new Location("33.780355", "-84.39928")));
            TotalBusStops_GT.add(new BusStop("fersstat_ob", new Location("33.778436", "-84.399961")));
            TotalBusStops_GT.add(new BusStop("fershemp_ob", new Location("33.778141", "-84.401829")));
            TotalBusStops_GT.add(new BusStop("reccent_ob", new Location("33.7751", "-84.40265")));
            TotalBusStops_GT.add(new BusStop("studcent", new Location("33.77335", "-84.39917")));

        }

        for (int i = 0; i < TotalBusStops_GT.size(); i++) {
            String Route;
            if (i < 4) {
                Route = "express";
            } else if (i < 25) {
                Route = "trolley";
            } else if (i < 42) {
                Route = "blue";
            } else if (i < 59) {
                Route = "red";
            } else {Route = "green";}

            if (!BusStop_Route.containsKey(TotalBusStops_GT.get(i).getStopid())) {

                busStops.add(TotalBusStops_GT.get(i));

                //System.out.println("@@@@@@@@" + TotalBusStops_GT.get(i).getLocation().getLatitude());
                ArrayList<Integer> busstop_route = new ArrayList<>();
                busstop_route.add(BusRouteId.get(Route));
                BusStop_Route.put(TotalBusStops_GT.get(i).getStopid(), busstop_route);


            } else {
                BusStop_Route.get(TotalBusStops_GT.get(i).getStopid()).add(BusRouteId.get(Route));
            }
        }

        ArrayList<Path> returnOptions = routeOption(user, dest, busStops, BusStop_Route);
        ArrayList<String> returnString = new ArrayList<>();

        /*******sort the returned option according to the total time and only retain 3 options with smallest total time******/
        Collections.sort(returnOptions, new Comparator<Path>() {
            @Override
            public int compare(Path x, Path y) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return  x.getTotal_time() > y.getTotal_time() ? 1 : (x.getTotal_time() < y.getTotal_time()) ? -1 : 0;
            }
        });

        // only retain 3 options
        if (returnOptions.size() > 3) {
            ListIterator listIterator = returnOptions.listIterator();
            listIterator.next();
            listIterator.next();
            listIterator.next();
            while (listIterator.hasNext()) {
                listIterator.remove();
                listIterator.next();
            }
        }

        // return the strings
        // getonid, getoffid, routeid, totaltime, nextbusid, arrivetime
        for (int i = 0; i < returnOptions.size(); i++) {
            returnString.add(returnOptions.get(i).getGeton_stop() + " " + returnOptions.get(i).getGetoff_stop() + " " + returnOptions.get(i).getRoute() + " " + Integer.toString(returnOptions.get(i).getTotal_time()));
        }
        return returnString;
    }

    public static void init(String[] args) {
        Location user = new Location("33.7757", "-84.4040");       // campus recreation center
        Location destination = new Location("33.7773", "-84.3962"); // klaus college of computing
        ArrayList<String> returnStrings = Solution(user, destination);
        for (int i = 0; i < returnStrings.size(); i++) {
            System.out.println(returnStrings.get(i));
        }
    }
}