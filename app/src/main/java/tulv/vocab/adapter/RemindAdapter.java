package tulv.vocab.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.model.LessonModel;

/**
 * Created by tulv on 10/28/2016.
 */
public class RemindAdapter extends BaseAdapter {
    ArrayList<Vocab> arrayList;
    LessonModel lessonModel;
    LayoutInflater inflater;
    MediaPlayer mediaPlayer;
    public RemindAdapter(Context context, ArrayList<Vocab> listData) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = listData;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Vocab item = arrayList.get(position);
        TextView tvEnglish, tvVietnamese;
        ImageView img;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_remind, parent, false);
        }
        tvEnglish = (TextView) convertView.findViewById(R.id.tvEnglish);
        tvVietnamese = (TextView) convertView.findViewById(R.id.tvVietnamese);
        img = (ImageView) convertView.findViewById(R.id.list_image);
        tvEnglish.setText(item.getEnglish());
        tvVietnamese.setText(item.getVietnamese());
        ImageButton bt=(ImageButton)convertView.findViewById(R.id.btSpeak);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eng=item.getEnglish().replace(' ','_');
                int resID = v.getResources().getIdentifier(eng, "raw", "tulv.vocab");
                mediaPlayer=MediaPlayer.create(v.getContext(), resID);
                mediaPlayer.start();
            }
        });
        byte[] blod=item.getImage();
        ByteArrayInputStream im=new ByteArrayInputStream(blod);
        img.setImageBitmap(BitmapFactory.decodeStream(im));
        return convertView;
    }
}
