package tulv.vocab.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.presenter.vocab.VocabPresenter;
import tulv.vocab.presenter.vocab.VocabPresenterImpl;

public class Game2Activity extends AppCompatActivity {
    VocabPresenter vocabPresenter;
    ArrayList<Vocab> arrayList;

    int location = 0;
    String answer = "";
    int total=0;
    int completion=0;

    TextView tvScore, tvTime;
    EditText edtVocab;
    ImageButton btSubmit, imgbt,btSpeak,btNext,btPre;

    MediaPlayer mediaPlayer;
    int rawID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        getWidget();
        mediaPlayer=new MediaPlayer();
        btSpeak.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View v) {
                        speak(v.getContext(),rawID);
                    }
        });
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
                showAlert("Hết giờ");
            }
        }.start();
        imgbt = (ImageButton) findViewById(R.id.btBack);
        imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game2Activity.this.finish();
                count.cancel();
            }
        });
        vocabPresenter = new VocabPresenterImpl(this);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("bundle");
        int id = bundle.getInt("id");

        tvScore = (TextView) findViewById(R.id.tvScore);

        arrayList = new ArrayList<>();
        arrayList = vocabPresenter.getListByLessonID(id);
        total=arrayList.size();

        String eng=arrayList.get(0).getEnglish().replace(' ','_');
        rawID = getResources().getIdentifier(eng, "raw", "tulv.vocab");
        speak(this,rawID);
        tvScore.setText("Hoàn thành: "+completion+"/"+total);
        answer = arrayList.get(location).getEnglish();
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String traloi=edtVocab.getText().toString();
                if (answer.equalsIgnoreCase(traloi)){
                    completion+=1;
                    tvScore.setText("Hoàn thành: "+completion+"/"+total);
                    edtVocab.setText("");
                    arrayList.remove(location);
                    location-=1;
                    if (location<0){
                        location=arrayList.size()-1;
                        if (location<0){
                            location=0;
                        }
                    }
                    if (arrayList.size()==0){
                        count.cancel();
                        showAlert("Hoàn thành");
                    }else {
                        answer = arrayList.get(location).getEnglish();
                        String eng=answer.replace(' ','_');
                        rawID = getResources().getIdentifier(eng, "raw", "tulv.vocab");
                        speak(view.getContext(),rawID);
                    }
                }else {
                    Toast.makeText(Game2Activity.this, "Bạn trả lời chưa chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location+=1;
                if (location>arrayList.size()-1){
                    location=0;
                }
                answer = arrayList.get(location).getEnglish();
                String eng=answer.replace(' ','_');
                rawID = getResources().getIdentifier(eng, "raw", "tulv.vocab");
                speak(view.getContext(),rawID);
            }
        });
        btPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location-=1;

                if (location<0){
                    location=arrayList.size()-1;
                    if (location<0){
                        location=0;
                    }
            }
                answer = arrayList.get(location).getEnglish();
                String eng=answer.replace(' ','_');
                rawID = getResources().getIdentifier(eng, "raw", "tulv.vocab");
                speak(view.getContext(),rawID);
            }
        });
    }

    private void getWidget() {
        edtVocab = (EditText) findViewById(R.id.edtVoCab);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btSubmit=(ImageButton)findViewById(R.id.btSubmit);
        btSpeak=(ImageButton)findViewById(R.id.btSpeak);
        btNext=(ImageButton)findViewById(R.id.btNext);
        btPre=(ImageButton)findViewById(R.id.btPre);
    }

    public void speak(Context context,int rawID){
        mediaPlayer=MediaPlayer.create(context, rawID);
        mediaPlayer.start();
    }
    public void showAlert(String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage("Hoàn thành: "+completion+"/"+total)
                .setCancelable(false)
                .setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Game2Activity.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}