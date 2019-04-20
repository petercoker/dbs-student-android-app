package dbs.ie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class Tools {

    //Add static of Database here  and call it from this call and use for

    public static AppDatabase database;
    String id;
    String name;
    String email;
    String username;
    String password;
    String userType;
    String avatar;
    String createDate;
    String lastLogin;
    String active;


    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            return status;
        }
        return status;
    }



    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array){
        List<Object> list = new ArrayList<Object>();
        try{
            for(int i = 0; i < array.length(); i++) {
                Object value = array.get(i);
                if(value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                } else if(value instanceof JSONObject) {
                    value = Tools.toMap((JSONObject) value);
                }
                list.add(value);
            }
        } catch (Exception ex){
            Log.e("Exception",ex.getMessage());
        }
        return  list;
    }


}