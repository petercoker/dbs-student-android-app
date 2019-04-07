package dbs.ie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends AppCompatActivity {
    EditText id;
    EditText name;
    EditText email;
    EditText username;
    EditText password;
    EditText userType;
    EditText avatar;
    EditText createDate;
    EditText lastLogin;
    EditText active;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        id = (EditText) findViewById(R.id.userid);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        userType = (EditText) findViewById(R.id.type);
        avatar = (EditText) findViewById(R.id.avatar);
        createDate = (EditText) findViewById(R.id.createDate);
        lastLogin = (EditText) findViewById(R.id.lastlogin);
        active = (EditText) findViewById(R.id.active);

        database = AppDatabase.getDatabase(getApplicationContext());

        findViewById(R.id.createUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(Integer.parseInt(id.getText().toString()), name.getText().toString(), email.getText().toString(),
                        username.getText().toString(), password.getText().toString(), userType.getText().toString(),
                        createDate.getText().toString(), avatar.getText().toString(), lastLogin.getText().toString(),
                        Integer.parseInt(active.getText().toString()));
                database.userDAO().addUser(user);

                Toast.makeText(getApplicationContext(),"User Added",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

