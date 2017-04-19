package gatech.nav;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

/**
 * Created by yzhu3 on 4/8/2017.
 */

public class BuildingSuggestion implements SearchSuggestion {
    private String mName;
    private String mAddress;
    private LatLng mLatLng;

    public void set(JsonObject jsonObject){
        this.mLatLng = new LatLng(Double.parseDouble(jsonObject.get("latitude").toString().substring(1, jsonObject.get("latitude").toString().length() - 1)), Double.parseDouble(jsonObject.get("longitude").toString().substring(1, jsonObject.get("longitude").toString().length() - 1)));
        this.mName = new String(jsonObject.get("name").toString().substring(1, jsonObject.get("name").toString().length() - 1));
        this.mAddress = new String(jsonObject.get("address").toString().substring(1, jsonObject.get("address").toString().length() - 1));
    }

    public String getAddress(){
        return mAddress;
    }

    public LatLng getLatLng(){
        return mLatLng;
    }

    @Override
    public String getBody(){
        return mName;
    }

    public static final Creator<BuildingSuggestion> CREATOR = new Creator<BuildingSuggestion>() {
        @Override
        public BuildingSuggestion createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public BuildingSuggestion[] newArray(int size) {
            return new BuildingSuggestion[0];
        }
    };

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){

    }
}
