package dbs.ie;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
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

public class RecyclerActivity extends AppCompatActivity implements ModuleAdapter.ItemClickListener {


    InternetConnector_Receiver internetConnectorReceiver = new InternetConnector_Receiver();
    private String userId;
    TextView name;
    public static Context applicationContext;
    public static RequestQueue queue;
    private AppDatabase database;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        name = (TextView)findViewById(R.id.name);
        Intent intent = getIntent();


        database = AppDatabase.getDatabase((getApplicationContext()));
        user = database.userDAO().getUser();
        name.setText(user.FullName.toString());

        applicationContext = getApplicationContext();

        if (RecyclerActivity.queue == null) {
            RecyclerActivity.queue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = getResources().getString(R.string.api_url) + "/Module/GetModulesForUser";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            Map responseApi = Tools.toMap(new JSONObject(response));
                            if (responseApi.get("status").toString().equals("success")) {
                                List<HashMap> moduleList = (ArrayList)responseApi.get("modules");


                                database = AppDatabase.getDatabase(getApplicationContext());

                                for(int i = 0; i < moduleList.size(); i++) {
                                    Map module = moduleList.get(i);

                                    String moduleId = module.get("Module_ID").toString();
                                    String moduleCode = module.get("Module_Code").toString();
                                    String moduleName = module.get("Module_Name").toString();
                                    String course = module.get("Course").toString();
                                    String courseIntake = module.get("Course_Intake").toString();
                                    String lecturer = module.get("Lecturer").toString();
                                    String startDate = module.get("StartDate").toString();
                                    String endDate = module.get("EndDate").toString();
                                    String start = module.get("From").toString();
                                    String end = module.get("To").toString();

                                    Module moduleItem = new Module(Integer.parseInt(moduleId),moduleCode, moduleName,
                                            course, courseIntake, lecturer, startDate, endDate, start, end);


                                    database.moduleDAO().addModule(moduleItem);


                                }

                                List<Module> modules = (ArrayList) database.moduleDAO().getAllModules();

                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                ModuleAdapter mAdapter = new ModuleAdapter(modules);
                                recyclerView.setAdapter(mAdapter);
                                ((ModuleAdapter) mAdapter).setmItemClickListener(RecyclerActivity.this);


                            } else {
                                Log.v("error", responseApi.get("modules").toString());
                            }

                        } catch (Exception e) {
                            Log.v("error", e.getMessage());

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Response", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User_ID", Integer.toString(user.User_ID));
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
    @Override
    public void onListClick(int position) {

        Intent intent = new Intent(RecyclerActivity.this, ContentActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetConnectorReceiver, filter);

    }

    protected void onStop() {
        super.onStop();
    }

}

