package gatech.nav;

import java.util.Calendar;
/**
 * Created by yzhu3 on 4/16/2017.
 */

public class BusSchedule {
    private static boolean Express = false;
    private static boolean Trolley = false;
    private static boolean Red = false;
    private static boolean Green = false;
    private static boolean Blue = false;

    private static Calendar c;
    private static int weekday;  //Sun->1;Mon->2;Tue->3;Wed->4;Thur->5;Fri->6;Sat->7;
    private static int timeNow;

    public static boolean getExpress(){
        return Express;
    }

    public static boolean getTrolley(){
        return Trolley;
    }

    public static boolean getRed(){
        return Red;
    }

    public static boolean getGreen(){
        return Green;
    }

    public static boolean getBlue(){
        return Blue;
    }

    public void init(){
        //Todo: set bus state to true if running, false if not running
        setWeekday();
        timeCompare();
        /*System.out.println(timeNow);
        System.out.println(weekday);
        System.out.println(Trolley);
        System.out.println(Red);
        System.out.println(Blue);
        System.out.println(Express);
        System.out.println(Green);*/
    }

    public void setWeekday() {
        //Todo: set Weekday according to system time
        c = Calendar.getInstance();
        weekday = c.get(Calendar.DAY_OF_WEEK);
        timeNow = c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.HOUR_OF_DAY) * 3600;

    }

    public void timeCompare(){
        if(weekday==1)
        {
            Express = false;
                if(timeNow<(21.75*3600)&&timeNow>(15*3600))
                {
                    Trolley = true;
                }
                else {
                    Trolley = false;
                }
            Red = false;
            Green = false;
            Blue = false;
        }
        else if(weekday==7)
        {
            Express = false;
            if(timeNow<(18.5*3600)&&timeNow>(10*3600))
            {
                Trolley = true;
            }
            else {
                Trolley = false;
            }
            Red = false;
            Green = false;
            Blue = false;
        }
        else
        {
            if(timeNow<(21.75*3600)&&timeNow>(7*3600))
            {Red = true;}
            else
            {Red = false;}
            if(timeNow<(22*3600)&&timeNow>(7*3600))
            {Blue = true;}
            else
            {Blue = false;}
            if(timeNow<(21*3600)&&timeNow>(6.75*3600))
            {Green = true;}
            else
            {Green = false;}
            if(timeNow<(22.5*3600)&&timeNow>(5.75*3600))
            {Trolley = true;}
            else
            {Trolley = false;}
            if(timeNow<(7.5*3600)&&timeNow>(6.5*3600))
            {Express = true;}
            else
            {Express = false;}
        }
    }
}
