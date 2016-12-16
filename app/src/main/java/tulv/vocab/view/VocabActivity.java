package tulv.vocab.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.adapter.VocabAdapter;
import tulv.vocab.presenter.vocab.VocabPresenter;
import tulv.vocab.presenter.vocab.VocabPresenterImpl;

public class VocabActivity extends AppCompatActivity {
    public int PAGES = 0;
    public final static int LOOPS = 1000;

    public VocabAdapter adapter;
    public ViewPager pager;

    ArrayList<Vocab> arrayList;
    VocabPresenter vocabPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreenToolBar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bund");
        int id = bundle.getInt("id");

        getSupportActionBar().setTitle(bundle.getString("name"));

        arrayList = new ArrayList<>();
        vocabPresenter = new VocabPresenterImpl(this);
        arrayList = vocabPresenter.getListByLessonID(id);
        PAGES = arrayList.size();
        pager = (ViewPager) findViewById(R.id.viewPagerVocab);

        adapter = new VocabAdapter(VocabActivity.this, VocabActivity.this.getSupportFragmentManager(), arrayList, 12, 6);
        pager.setAdapter(adapter);
        pager.setPageTransformer(false, adapter);

        pager.setCurrentItem(PAGES / 2);
        pager.setOffscreenPageLimit(1);
        pager.setPageMargin(10);
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