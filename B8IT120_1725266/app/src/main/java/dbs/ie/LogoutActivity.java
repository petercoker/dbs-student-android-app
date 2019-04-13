package dbs.ie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LogoutActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        database = AppDatabase.getDatabase(getApplicationContext());

        findViewById(R.id.action_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = AppDatabase.getDatabase(getApplicationContext());
                database.userDAO().removeAllUsers();

                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                LogoutActivity.this.startActivity(intent);

                Toast.makeText(getApplicationContext(),"User Logged out",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
