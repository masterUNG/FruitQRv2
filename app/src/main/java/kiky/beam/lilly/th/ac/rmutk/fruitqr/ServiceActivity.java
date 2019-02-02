package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle; //ทำการเชื่อม  toolbar กับ ActionBarDrawerToggle
    private String idString, nameUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        getUser();

    } //Main Method

    private void createToobar() {
        Toolbar toolbar = findViewById(R.id.toobarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(nameUserString);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_hamberger); //ทำแถบเมณูเป็น Hamberger

//        Create Hamberger Icon
        drawerLayout = findViewById(R.id.layoutDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ServiceActivity.this, drawerLayout, R.string.open,R.string.close); //ต้องใส่ค่า open และ close ใน string
    }

    private void getUser() {
        idString = getIntent().getStringExtra("id");
        try {

            Myconstant myconstant = new Myconstant();
            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(ServiceActivity.this);
            getDataWhereOneColumn.execute("id", idString, myconstant.getUrlGetUserWhereId());
            String json = getDataWhereOneColumn.get();

            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            nameUserString = jsonObject.getString("Name");

            createToobar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //ทำแถบเมณูที่เปิดออกมาจากด้านซ้ายมือ
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itemQR){
        startActivity(new Intent(ServiceActivity.this, QRActivity.class));

        }

        if(item.getItemId() == R.id.itemExit){
            Log.d("7janv1","you click");
            finish();
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }    //ทำแถบเมณูถึงนี่

} //Main Class
