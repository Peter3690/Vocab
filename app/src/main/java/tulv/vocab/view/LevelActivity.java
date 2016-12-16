package tulv.vocab.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import tulv.vocab.Entities.Level;
import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.Transformer.CubeOutTransformer;
import tulv.vocab.adapter.LevelPageAdapter;
import tulv.vocab.presenter.level.LevelPresenter;
import tulv.vocab.presenter.level.LevelPresenterImpl;
import tulv.vocab.presenter.vocab.VocabPresenter;
import tulv.vocab.presenter.vocab.VocabPresenterImpl;


public class LevelActivity extends AppCompatActivity {
    private ViewPager mPager;
    private LevelPageAdapter mAdapter;
    ArrayList<Level> arrayList;
    VocabPresenter vocabPresenter;
    LevelPresenter levelPresenter;

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    int mNotificationId = 001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreenToolBar));
        getSupportActionBar().setTitle("Từ vựng tiếng Anh VOCAB");
//////////////////////////////////////////////////////////////////////
        vocabPresenter = new VocabPresenterImpl(this);
        ArrayList<Vocab> arrayListVocabs = new ArrayList<>();
        arrayListVocabs = vocabPresenter.getListRemind();
        final int count = arrayListVocabs.size();
        final Vocab[] vocab = new Vocab[1];
        final ArrayList<Vocab> finalArrayListVocabs = arrayListVocabs;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < count; i++) {
                    vocab[0] = finalArrayListVocabs.get(i);
                    SystemClock.sleep(900000);
                    mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.mipmap.vocabularyicon)
                                    .setContentTitle(vocab[0].getEnglish())
                                    .setContentText(vocab[0].getVietnamese());
                    mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Intent resultIntent = new Intent(getApplicationContext(), RemindActivity.class);
                    resultIntent.putExtra("id", vocab[0].getId());

                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    getApplicationContext(),
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    Notification notification = mBuilder.build();
                    notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
                    mNotifyMgr.notify(1, notification);
                }
            }
        });
        thread.start();
        ///////////////////////////////////////////////////////////////////////////
        levelPresenter = new LevelPresenterImpl(getApplicationContext());
        arrayList = new ArrayList<>();
        arrayList = levelPresenter.getList();
        mAdapter = new LevelPageAdapter(this.getSupportFragmentManager(), arrayList);

        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        mPager.setPageTransformer(true, new CubeOutTransformer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
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
}
