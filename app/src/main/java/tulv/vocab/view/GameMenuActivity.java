package tulv.vocab.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tulv.vocab.R;

public class GameMenuActivity extends AppCompatActivity {
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreenToolBar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        id = bundle.getInt("id");

        getSupportActionBar().setTitle(bundle.getString("name"));
    }
    public void choseGame(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.btGame1:
                intent=new Intent(GameMenuActivity.this, Game1Activity.class);
                break;
            case R.id.btGame2:
                 intent=new Intent(GameMenuActivity.this, Game2Activity.class);
                break;
        }
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        Intent intent = null;
        switch (id) {
            case R.id.about:
                intent = new Intent(getApplicationContext(), About.class);
                break;
            case R.id.remind:
                intent = new Intent(getApplicationContext(), RemindActivity.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
