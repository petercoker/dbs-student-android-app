package dbs.ie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class UserList extends AppCompatActivity {

    private AppDatabase database;
    ListView listView;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        findViewById(R.id.addNewUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserList.this, AddUser.class);
                startActivity(intent);
            }
        });

        database = AppDatabase.getDatabase(getApplicationContext());

        listView = (ListView)findViewById(R.id.userList);
        List<User> users = database.userDAO().getAllUsers();

        if (users.size() !=0 ) {
            list = new String[users.size()];
            for (int i = 0; i < users.size(); i++) {
                list[i] = users.get(i).FullName;
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, list);
            listView.setAdapter(adapter);
        }
    }
}
