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
    MediaPlayer mediaPlayer;
    Context context;
    ReadWriteFile readWriteFile;

    // Hàm tạo của custom
    public PronounciationAdapter(Context context, ArrayList<Vocab> listData) {
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
        ViewHolder viewHolder = null;
        // Khởi tạo view.
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.list_row_pronounce,parent,false);
            viewHolder=new ViewHolder();

            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.tvSTT);
            viewHolder.tvEnglish = (TextView) convertView.findViewById(R.id.tvEnglish);
            viewHolder.tvPronoun = (TextView) convertView.findViewById(R.id.tvPronoun);
            viewHolder.tvCheck = (TextView) convertView.findViewById(R.id.tvCheck);
            viewHolder.btnSpeak = (ImageButton) convertView.findViewById(R.id.btSound);
            viewHolder.btnCheck = (Button) convertView.findViewById(R.id.btCheck);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        lessonModel = new LessonModel(convertView.getContext());
        mediaPlayer = new MediaPlayer();

        viewHolder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=item.getId();
                vitri = position;
                promptSpeechInput((Activity) parent.getContext());
            }
        });
        viewHolder.btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String eng = item.getEnglish().replace(' ', '_');
                    int resID = parent.getResources().getIdentifier(eng, "raw", "tulv.vocab");
                    mediaPlayer = MediaPlayer.create(parent.getContext(), resID);
                    mediaPlayer.start();
                }catch (Exception e){
                    Toast.makeText(view.getContext(), "Không có âm thanh", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Set dữ liệu vào item của list view
        viewHolder.tvNumber.setText(position + 1 + ".");
        viewHolder.tvEnglish.setText(item.getEnglish());
        viewHolder.tvPronoun.setText(item.getPronoun());
        String result=readWriteFile.readData(item.getId(),"pronounciation.txt");
        viewHolder.tvCheck.setText(result);
        return convertView;
    }

    public class ViewHolder{
        TextView tvNumber;
        TextView tvEnglish;
        TextView tvPronoun;
        TextView tvCheck;
        ImageButton btnSpeak;
        Button btnCheck;
    }

    private void promptSpeechInput(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Đọc");
        try {
            activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(activity,
                    "Vui lòng kiểm tra kết nối Internet!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}