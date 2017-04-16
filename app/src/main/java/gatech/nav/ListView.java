package gatech.nav;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


/**
 * Created by yzhu3 on 4/15/2017.
 */

public class ListView extends ListFragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        String[] values = new String[] {/* "Android", "iPhone", "WindowsMobile" */};
        RouteAdaptor adapter = new RouteAdaptor(getActivity(),R.layout.list_view,values);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        //Toast.makeText(getActivity(),"Item: " + position, Toast.LENGTH_SHORT).show();
    }

}


