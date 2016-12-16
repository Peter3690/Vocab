package tulv.vocab.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.model.LessonModel;
import tulv.vocab.model.ReadWriteFile;

/**
 * Created by tulv on 12/1/2016.
 */
public class PronounciationAdapter extends BaseAdapter {
    public static int vitri = -1;
    public static int id = -1;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ArrayList<Vocab> arrayList;
    LessonModel lessonModel;
    LayoutInflater inflater;
    MediaPlayer mediaPlayer;
    Context context;
    ReadWriteFile readWriteFile;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // Hàm tạo của custom
    public PronounciationAdapter(Context context, ArrayList<Vocab> listData) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = listData;
        this.context = context;
        this.readWriteFile=new ReadWriteFile(context);
    }
    // Trả về số lượng phần tử được hiển thị trong listview
    @Override
    public int getCount() {
        return arrayList.size();
    }

    // Trả về đối tượng được lấy theo vị trí
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Hàm quan trọng nhất, hiển thị giao diện của listview
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Lấy ra đối tượng cần hiển thị ở vị trí thứ position
        final Vocab item = arrayList.get(position);
        // Khai báo các component
        TextView tvEnglish, tvPronoun, tvCheck, tvSTT;
        ImageButton btSpeak;
        Button btCheck;
        // Khởi tạo view.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_pronounce, parent, false);
        }
        lessonModel = new LessonModel(convertView.getContext());
        mediaPlayer = new MediaPlayer();
        tvSTT = (TextView) convertView.findViewById(R.id.tvSTT);
        tvEnglish = (TextView) convertView.findViewById(R.id.tvEnglish);
        tvPronoun = (TextView) convertView.findViewById(R.id.tvPronoun);
        tvCheck = (TextView) convertView.findViewById(R.id.tvCheck);
        btSpeak = (ImageButton) convertView.findViewById(R.id.btSound);
        btCheck = (Button) convertView.findViewById(R.id.btCheck);
        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=item.getId();
                vitri = position;
                promptSpeechInput((Activity) parent.getContext());
            }
        });
        btSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eng = item.getEnglish().replace(' ', '_');
                int resID = parent.getResources().getIdentifier(eng, "raw", "tulv.vocab");
                mediaPlayer = MediaPlayer.create(parent.getContext(), resID);
                mediaPlayer.start();
            }
        });
        // Set dữ liệu vào item của list view
        tvSTT.setText(position + 1 + ".");
        tvEnglish.setText(item.getEnglish());
        tvPronoun.setText(item.getPronoun());
        String result=readWriteFile.readData(item.getId(),"pronounciation.txt");
        tvCheck.setText(result);
        return convertView;
    }

    private void promptSpeechInput(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "noi di");
        try {
            activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(activity,
                    "khong ho tro",
                    Toast.LENGTH_SHORT).show();
        }
    }
}