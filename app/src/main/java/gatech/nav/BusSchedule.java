package gatech.nav;

import java.sql.Time;

/**
 * Created by yzhu3 on 4/16/2017.
 */

public class BusSchedule {
    private boolean Express = false;
    private boolean Trolley = false;
    private boolean Red = false;
    private boolean Green = false;
    private boolean Blue = false;

    private int Weekday;  //Mon->0;Tue->1;Wed->2;Thur->3;Fri->4;Sat->5;Sun->6

    public boolean getExpress(){
        return Express;
    }

    public boolean getTrolley(){
        return Trolley;
    }

    public boolean getRed(){
        return Red;
    }

    public boolean getGreen(){
        return Green;
    }

    public boolean getBlue(){
        return Blue;
    }

    public void init(){
        //Todo: set bus state to true if running, false if not running
        setWeekday();
    }

    public void setWeekday(){
        //Todo: set Weekday according to system time
    }

    public boolean timeCompare(Time time1, Time time2){
        //Todo: compare two time, if time1 <= time2 return true,else return false
        return true;
    }
}
