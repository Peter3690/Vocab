package tulv.vocab.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.adapter.Game1Adapter;
import tulv.vocab.presenter.vocab.VocabPresenter;
import tulv.vocab.presenter.vocab.VocabPresenterImpl;

public class Game1Activity extends AppCompatActivity {
    TextView tvScore, tvVietnamese, tvEnglish, tvTime;
    ImageButton imgbt;
    GridView grAnphabet;

    VocabPresenter vocabPresenter;
    ArrayList<String> list;
    ArrayList<Vocab> arrayList;
    int location = 0;
    String rootWord = "";
    String answer = "";
    Game1Adapter adapter;
    ArrayList<String> s1;
    Random rd;
    int score = 0;
    int highScore = 0;


    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        final CountDownTimer count = new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {

                if (millisUntilFinished / 1000 >= 60) {
                    if ((millisUntilFinished / 1000) - 60 < 10) {
                        tvTime.setText("1:0" + ((millisUntilFinished / 1000) - 60));
                    } else {
                        tvTime.setText("1:" + ((millisUntilFinished / 1000) - 60));
                    }

                }
                if (millisUntilFinished / 1000 < 60) {
                    if (millisUntilFinished / 1000 < 10) {
                        tvTime.setText("0:0" + (millisUntilFinished / 1000));
                    } else {
                        tvTime.setText("0:" + (millisUntilFinished / 1000));
                    }

                }
            }

            public void onFinish() {
                tvTime.setText("0:00");
                showAlert();
            }
        }.start();
        imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game1Activity.this.finish();
                count.cancel();
            }
        });
        vocabPresenter = new VocabPresenterImpl(this);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("bundle");
        int id = bundle.getInt("id");
        arrayList = new ArrayList<>();
        arrayList = vocabPresenter.getListByLessonID(id);
        list = new ArrayList<>();
        rd = new Random();
        int kk = rd.nextInt(arrayList.size());
        location = kk;
        rootWord = arrayList.get(location).getEnglish();
        tvVietnamese.setText(arrayList.get(location).getVietnamese());
        s1 = new ArrayList<String>();
        for (int k = 0; k < rootWord.length(); k++) {
            s1.add(Character.toString(rootWord.charAt(k)));
        }
        list = creatData(rootWord);
        adapter = new Game1Adapter(this, list);
        grAnphabet.setAdapter(adapter);
        //Sự kiện click vào mỗi item trong gridview
        grAnphabet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).equalsIgnoreCase(s1.get(0))) {
                    answer += s1.get(0);
                    View v = grAnphabet.getChildAt(i);
                    v.setVisibility(View.INVISIBLE);
                    s1.remove(0);
                    tvEnglish.setText(answer);
                } else {
                    score--;
                    tvScore.setText("Điểm: " + score);
                }
                if (s1.size() == 0) {
                    String eng = answer.replace(' ', '_');
                    int resID = getResources().getIdentifier(eng, "raw", "tulv.vocab");
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
                    mediaPlayer.start();
                    answer = "";
                    tvEnglish.setText(answer);
                    score += 10;
                    tvScore.setText("Điểm: " + score);
                    rd = new Random();
                    location = rd.nextInt(arrayList.size());
                    if (location < arrayList.size()) {
                        rootWord = arrayList.get(location).getEnglish();
                        tvVietnamese.setText(arrayList.get(location).getVietnamese());
                        s1 = new ArrayList<String>();
                        for (int k = 0; k < rootWord.length(); k++) {
                            s1.add(Character.toString(rootWord.charAt(k)));
                        }
                        list = creatData(rootWord);
                        adapter = new Game1Adapter(view.getContext(), list);
                        grAnphabet.setAdapter(adapter);
                    }
                }
            }
        });
    }

    public void getWidget() {
        tvTime = (TextView) findViewById(R.id.tvTime);
        imgbt = (ImageButton) findViewById(R.id.btBack);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvVietnamese = (TextView) findViewById(R.id.tvVietnamese);
        tvEnglish = (TextView) findViewById(R.id.tvEnglish);
        grAnphabet = (GridView) findViewById(R.id.grAnphabet);
    }

    public ArrayList<String> creatData(String s) {
        ArrayList<String> listOutput = new ArrayList<>();
        ArrayList<String> listInput = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            listInput.add(Character.toString(s.charAt(i)));
        }
        Random random = new Random();
        int j;
        while (listInput.size() != 0) {
            j = random.nextInt(listInput.size());
            listOutput.add(listInput.get(j));
            listInput.remove(j);
        }
        return listOutput;
    }

    public void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle("Hết giờ");
        // set dialog message
        alertDialogBuilder
                .setMessage("Số điểm của bạn là: " + score + "/" + highScore)
                .setCancelable(false)
                .setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Game1Activity.this.finish();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}

