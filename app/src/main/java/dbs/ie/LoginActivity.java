package dbs.ie;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    public static RequestQueue queue ;
    public EditText email;
    public EditText password;
    public AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoginActivity.queue == null) {
                    LoginActivity.queue = Volley.newRequestQueue(getApplicationContext());
                }

                String url = getResources().getString(R.string.api_url)+"/User/Login";

                final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Map loginResponse = Tools.toMap(new JSONObject(response));
                                    if(loginResponse.get("status").toString().equals("success")){
                                        Log.v("user", loginResponse.get("user").toString());

                                        Map loginUser = (HashMap)loginResponse.get("user");
                                        database = AppDatabase.getDatabase(getApplicationContext());


                                        String id = loginUser.get("User_ID").toString();
                                        String name = loginUser.get("FullName").toString();
                                        String email_1 =loginUser.get("Email").toString();
                                        String username = loginUser.get("Username").toString();
                                        String password_1 = loginUser.get("Password").toString();
                                        String userType = loginUser.get("User_Type").toString();
                                        String avatar = loginUser.get("Avatar").toString();
                                        String createDate = loginUser.get("DateCreated").toString();
                                        String lastLogin = loginUser.get("LastLogin").toString();
                                        String active = loginUser.get("Active").toString();

                                        User user = new User(Integer.parseInt(id), name, email_1, username, password_1, userType, avatar, createDate, lastLogin, Integer.parseInt(active));
                                        database.userDAO().addUser(user);

                                        Intent intent = new Intent(LoginActivity.this, ModuleActivity.class);
                                        startActivity(intent);
                                        finish();



                                    } else {
                                        Log.v("error", loginResponse.get("message").toString());
                                        Toast.makeText(getApplicationContext(), "invalid details entered", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.v("Error:", "Error Creating JSON object");
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
                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginActivity.queue.add(stringRequest);
                    }
                }, 200);




            }
        });

        //endregion
    }

}