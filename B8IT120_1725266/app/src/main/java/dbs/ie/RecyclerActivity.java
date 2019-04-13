package dbs.ie;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//AppCompatActivity it shows the top
public class RecyclerActivity extends AppCompatActivity {

    public static RequestQueue queue;
    public static Context applicationContext;
    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        applicationContext = getApplicationContext();

        if(RecyclerActivity.queue == null) {
            RecyclerActivity.queue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = getResources().getString(R.string.api_url)+"/Module/GetModulesForUser";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Map apiResponse = Tools.toMap(new JSONObject(response));
                            if(apiResponse.get("status").toString().equals("success")){

                                List<Object> modules = (ArrayList)apiResponse.get("modules");
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                RecyclerView.Adapter mAdapter = new ModuleAdapter(modules);
                                recyclerView.setAdapter(mAdapter);

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
                RecyclerActivity.queue.add(stringRequest);
            }
        }, 200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Module page refreshed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_logout:
                database = AppDatabase.getDatabase(getApplicationContext());
                final int SPLASH_DISPLAY_LENGTH = 1000;

                if(!(database.userDAO().getAllUsers().isEmpty())){
                    database.userDAO().removeAllUsers();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(RecyclerActivity.this,LoginActivity.class);
                            RecyclerActivity.this.startActivity(mainIntent);
                            RecyclerActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);

                }

                Toast.makeText(getApplicationContext(),"User Logged out",Toast.LENGTH_SHORT).show();
            break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings options", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
