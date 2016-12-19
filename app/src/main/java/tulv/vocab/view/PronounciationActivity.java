package tulv.vocab.view;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.adapter.PronounciationAdapter;
import tulv.vocab.model.ReadWriteFile;
import tulv.vocab.presenter.vocab.VocabPresenter;
import tulv.vocab.presenter.vocab.VocabPresenterImpl;

public class PronounciationActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ListView lvData;
    PronounciationAdapter adapter = null;
    ArrayList<Vocab> arrayList;
    VocabPresenter vocabPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronounciation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreenToolBar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("bundle");
        int id = bundle.getInt("id");
        vocabPresenter=new VocabPresenterImpl(this);
        arrayList = new ArrayList<>();
        arrayList =vocabPresenter.getListByLessonID(id);

        getSupportActionBar().setTitle(bundle.getString("name"));

        lvData = (ListView) findViewById(R.id.lvData);
        adapter = new PronounciationAdapter(this, arrayList);
        lvData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    View v= getViewByPosition(adapter.vitri,lvData);
                    TextView tv=(TextView)v.findViewById(R.id.tvCheck);
                    tv.setText(result.get(0));
                    ReadWriteFile readWriteFile=new ReadWriteFile(this);
                   readWriteFile.writeData(adapter.id,"pronounciation.txt",result.get(0));
                }
                break;
            }
        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        try {
            final int firstListItemPosition = listView
                    .getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition
                    + listView.getChildCount() - 1;

            if (pos < firstListItemPosition || pos > lastListItemPosition) {
                //This may occure using Android Monkey, else will work otherwise
                return listView.getAdapter().getView(pos, null, listView);
            } else {
                final int childIndex = pos - firstListItemPosition;
                return listView.getChildAt(childIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}