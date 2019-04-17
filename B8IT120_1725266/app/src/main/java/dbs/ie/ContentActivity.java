package dbs.ie;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentActivity extends AppCompatActivity {

    public static RequestQueue queue;
    public static Context applicationContext;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        applicationContext = getApplicationContext();

        if(ContentActivity.queue == null) {
            ContentActivity.queue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = getResources().getString(R.string.api_url)+"/Module/GetModuleContent";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Map apiResponse = Tools.toMap(new JSONObject(response));
                            if(apiResponse.get("status").toString().equals("success")){

//                                List<Object> modules = (ArrayList)apiResponse.get("modules");
//                                ContentActivity recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//                                ContentActivity.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                                ContentActivity.setLayoutManager(layoutManager);
//                                ContentActivity.Adapter mAdapter = new ContentAdapter(module_content);
//                                ContentActivity.setAdapter(mAdapter);

                            } else {
                                Log.v("error", apiResponse.get("message").toString());
                            }
                        } catch (Exception e) {
                            Log.v("Error:", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Response is:",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User_ID", "1");
                params.put("ForApp", "true");
                return params;
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ContentActivity.queue.add(stringRequest);
            }
        }, 200);
    }
}
