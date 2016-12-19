package tulv.vocab.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.adapter.RemindAdapter;
import tulv.vocab.presenter.vocab.VocabPresenter;
import tulv.vocab.presenter.vocab.VocabPresenterImpl;

public class RemindActivity extends AppCompatActivity {
    ListView lvData;
    RemindAdapter adapter = null;
    ArrayList<Vocab> arrayList;
    VocabPresenter vocabPresenter;

    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreenToolBar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Từ vựng nhắc nhở");

        arrayList = new ArrayList<>();
        vocabPresenter = new VocabPresenterImpl(this);
        arrayList = vocabPresenter.getListRemind();
        lvData = (ListView) findViewById(R.id.lvData);
        try {
            Intent intent = getIntent();
            int id = intent.getExtras().getInt("id");
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getId() == id) {
                    position = i;
                }
            }
        } catch (Exception e) {

        }
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        adapter = new RemindAdapter(this, arrayList);
        lvData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        lvData.smoothScrollToPosition(position);
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        Intent intent = null;
        switch (id) {
            case R.id.about:
                intent = new Intent(getApplicationContext(), AboutActivity.class);
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