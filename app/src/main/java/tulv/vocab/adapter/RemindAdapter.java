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
import android.widget.Toast;

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
    MediaPlayer mediaPlayer;
    Context context;
    public RemindAdapter(Context context, ArrayList<Vocab> listData) {
        this.arrayList = listData;
        this.context=context;
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
        ViewHolder viewHolder;
        TextView tvEnglish, tvVietnamese;
        ImageView img;
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.list_row_remind,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tvEnglish = (TextView) convertView.findViewById(R.id.tvEnglish);
            viewHolder.tvVietnamese = (TextView) convertView.findViewById(R.id.tvVietnamese);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.ibSound=(ImageButton)convertView.findViewById(R.id.btSpeak);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tvEnglish.setText(item.getEnglish());
        viewHolder.tvVietnamese.setText(item.getVietnamese());
        viewHolder.ibSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String eng=item.getEnglish().replace(' ','_');
                    int resID = v.getResources().getIdentifier(eng, "raw", "tulv.vocab");
                    mediaPlayer=MediaPlayer.create(v.getContext(), resID);
                    mediaPlayer.start();
                }catch (Exception e){
                    Toast.makeText(v.getContext(), "Không có âm thanh", Toast.LENGTH_SHORT).show();
                }
            }
        });
        byte[] blod=item.getImage();
        ByteArrayInputStream im=new ByteArrayInputStream(blod);
        viewHolder.ivImage.setImageBitmap(BitmapFactory.decodeStream(im));
        return convertView;
    }
    public class ViewHolder{
        TextView tvEnglish;
        TextView tvVietnamese;
        ImageButton ibSound;
        ImageView ivImage;
    }
}
