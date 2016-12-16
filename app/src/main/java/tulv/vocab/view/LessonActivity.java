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
import android.widget.Toast;

import java.util.ArrayList;

import tulv.vocab.Entities.Lesson;
import tulv.vocab.R;
import tulv.vocab.adapter.LessonAdapter;
import tulv.vocab.presenter.lesson.LessonPresenter;
import tulv.vocab.presenter.lesson.LessonPresenterImpl;

public class LessonActivity extends AppCompatActivity {
    ListView lvData;
    LessonAdapter adapter = null;
    ArrayList<Lesson> arrayList;
    LessonPresenter lessonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreenToolBar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        int id = bundle.getInt("id");

        getSupportActionBar().setTitle(bundle.getString("name"));

        arrayList = new ArrayList<>();
        lessonPresenter = new LessonPresenterImpl(this);
        arrayList = lessonPresenter.getListByLevelId(id);
        //Toast.makeText(LessonActivity.this, ""+arrayList.size(), Toast.LENGTH_SHORT).show();
        if (arrayList.size() == 0) {
            Toast.makeText(LessonActivity.this, "Không có bài học nào!", Toast.LENGTH_SHORT).show();
        } else {
            lvData = (ListView) findViewById(R.id.lvData);
            //sử dụng hàm setOnItemClickListener
            lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), VocabActivity.class);
                    Bundle bund = new Bundle();
                    bund.putInt("id", arrayList.get(position).getId());
                    bund.putString("name", arrayList.get(position).getName());
                    intent.putExtra("bund", bund);
                    startActivity(intent);
                }
            });
            adapter = new LessonAdapter(this, arrayList);
            lvData.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
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