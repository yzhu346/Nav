package gatech.nav;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by yzhu3 on 4/15/2017.
 */

public class RouteAdaptor extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    ArrayList<String> data = null;

    public RouteAdaptor(Context context, int layoutResourceId, ArrayList<String> data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<String> d){
        data = d;
    }

    //data[0] start stop; data[1] end stop; data[2] route; data[3] total time; data[4] next bus time to start stop; data[5] next bus id
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        RouteHolder holder = null;
        String start_stop = new String();
        String end_stop = new String();
        String route_id = new String();
        String total_time = new String();


        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RouteHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.icon);
            holder.text1 = (TextView) row.findViewById(R.id.firstLine);
            holder.text2 = (TextView) row.findViewById(R.id.secondLine);

            row.setTag(holder);
        }
        else
            holder = (RouteHolder)row.getTag();

        String route = data.get(position);
        StringTokenizer st = new StringTokenizer(route);
        while (st.hasMoreElements()) {
            start_stop = st.nextElement().toString();
            end_stop = st.nextElement().toString();
            route_id = st.nextElement().toString();
            total_time = st.nextElement().toString();
        }
        int time = Integer.parseInt(total_time);
        int time_min = time/60;

        switch (route_id) {
            case "trolley" :
                holder.imgIcon.setImageResource(R.drawable.trolley);
                holder.text1.setText("Trolley");
                holder.text2.setText(time_min + " mins");
                break;
            case "express" :
                holder.imgIcon.setImageResource(R.drawable.express);
                holder.text1.setText("Express");
                holder.text2.setText(time_min + " mins");
                break;
            case "red" :
                holder.imgIcon.setImageResource(R.drawable.red);
                holder.text1.setText("Red");
                holder.text2.setText(time_min + " mins");
                break;
            case "green" :
                holder.imgIcon.setImageResource(R.drawable.green);
                holder.text1.setText("Green");
                holder.text2.setText(time_min + " mins");
                break;
            case "blue" :
                holder.imgIcon.setImageResource(R.drawable.blue);
                holder.text1.setText("Blue");
                holder.text2.setText(time_min + " mins");
                break;
            case "walk" :
                holder.imgIcon.setImageResource(R.drawable.walk);
                holder.text1.setText("Walk");
                holder.text2.setText(time_min + " mins");
                break;

        }

        return row;
    }

    static class RouteHolder{
        ImageView imgIcon;
        TextView text1;
        TextView text2;
    }
}

/*
public class RouteAdaptor implements Adapter {

    @Override
    public void registerDataSetObserver(DataSetObserver observer){

    }

    @Override
    public boolean isEmpty(){
        return false;
    }

    @Override
    public int getCount(){

    }

}
*/
