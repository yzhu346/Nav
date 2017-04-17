package gatech.nav;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yzhu3 on 4/15/2017.
 */

public class RouteAdaptor extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    String[] data = null;

    public RouteAdaptor(Context context, int layoutResourceId, String[] data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    //data[0] start stop; data[1] end stop; data[2] route; data[3] total time; data[4] next bus time to start stop; data[5] next bus id
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        RouteHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RouteHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.icon);
            holder.text = (TextView) row.findViewById(R.id.firstLine);

            row.setTag(holder);
        }
        else
            holder = (RouteHolder)row.getTag();

        String route = data[position];
        holder.text.setText(route);
        holder.imgIcon.setImageResource(R.drawable.trolley);

        return row;
    }

    static class RouteHolder{
        ImageView imgIcon;
        TextView text;
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
