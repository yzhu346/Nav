package gatech.nav;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;



/**
 * Created by yzhu3 on 4/15/2017.
 */

public class Listview extends Fragment implements AdapterView.OnItemClickListener {
    private static ArrayList<String> values;
    private static RouteAdaptor adapter;
    private static ListView list;
    private static Context context;

    public void setValues(ArrayList<String> data){
        values = data;
        list.setAdapter(new RouteAdaptor(context, R.layout.list_view, values));
        adapter.setData(values);
        adapter.notifyDataSetChanged();
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        final ListView lv = (ListView)view.findViewById(android.R.id.list);
        list = lv;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        values = new ArrayList<String>();
        context = getActivity();
        adapter = new RouteAdaptor(getActivity(), R.layout.list_view, values);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        //Toast.makeText(getActivity(),"Item: " + position, Toast.LENGTH_SHORT).show();
    }


}


