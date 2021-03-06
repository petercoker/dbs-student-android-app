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
import android.widget.Toast;

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
    private AppDatabase database;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        final List<Object> modules;
        final Map subtopic = new HashMap();
        database = AppDatabase.getDatabase(getApplicationContext());
        module = database.moduleDAO().getModule();


        if (ContentActivity.queue == null) {
            ContentActivity.queue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = getResources().getString(R.string.api_url) + "/Module/GetModuleContent";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Map apiResponse = Tools.toMap(new JSONObject(response));
                            if(apiResponse.get("status").toString().equals("success")){
                                List<Object> modules = (ArrayList)apiResponse.get("topics");
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.module_content);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                RecyclerView.Adapter mAdapter = new ContentAdapter(modules);
                                recyclerView.setAdapter(mAdapter);

                            } else {
                                Log.v("error", apiResponse.get("message").toString());
                            }

                        }
                        catch(Exception e){
                            Log.v("Error", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Module_ID", Integer.toString(module.Module_ID));
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
        },200);

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
                Toast.makeText(this, "Content page refreshed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_logout:
                database = AppDatabase.getDatabase(getApplicationContext());
                final int SPLASH_DISPLAY_LENGTH = 1000;

                if(!(database.userDAO().getAllUsers().isEmpty())){
                    database.userDAO().removeAllUsers();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(ContentActivity.this,LoginActivity.class);
                            ContentActivity.this.startActivity(mainIntent);
                            ContentActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);

                }

                Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
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