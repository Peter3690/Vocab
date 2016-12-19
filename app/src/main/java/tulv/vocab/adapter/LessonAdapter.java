package tulv.vocab.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import tulv.vocab.Entities.Lesson;
import tulv.vocab.R;
import tulv.vocab.model.LessonModel;
import tulv.vocab.view.GameMenuActivity;
import tulv.vocab.view.PronounciationActivity;

/**
 * Created by tulv on 10/18/2016.
 */
public class LessonAdapter extends BaseAdapter {
    ArrayList<Lesson> arrayList;
    LessonModel lessonModel;
Context context;
    // Hàm tạo của custom
    public LessonAdapter(Context context, ArrayList<Lesson> listData) {
        this.arrayList = listData;
this.context=context;
    }

    String name = "";

    public int getFlagResource(Context context, String name) {
        int resId = context.getResources().getIdentifier(name, "drawable", "tulv.vocab");
        return resId;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Lấy ra đối tượng cần hiển thị ở vị trí thứ position
        final Lesson item = arrayList.get(position);
        ViewHolder viewHolder = null;
        // Khởi tạo view.
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.list_row_lesson,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvLessonName = (TextView) convertView.findViewById(R.id.tvLessonName);
            viewHolder.tvLessonNumber = (TextView) convertView.findViewById(R.id.tvLessonNumber);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.btnGame = (ImageButton) convertView.findViewById(R.id.btGame);
            viewHolder.btnPronoun = (ImageButton) convertView.findViewById(R.id.btPronoun);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        lessonModel = new LessonModel(convertView.getContext());

        viewHolder.btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), GameMenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());

                bundle.putString("name", item.getName());
                intent.putExtra("bundle", bundle);
                parent.getContext().startActivity(intent);
            }
        });
        viewHolder.btnPronoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), PronounciationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("name", item.getName());
                intent.putExtra("bundle", bundle);
                parent.getContext().startActivity(intent);
            }
        });
        // Set dữ liệu vào item của list view
        viewHolder.tvLessonName.setText(item.getName());
       /* txtPhone.setText(item.getPhone());*/
        int count = lessonModel.countVocab(item.getId());
        viewHolder.tvLessonNumber.setText("Số từ vựng là: " + count);
        byte[] blod = item.getImage();
        ByteArrayInputStream im = new ByteArrayInputStream(blod);
        viewHolder.ivImage.setImageBitmap(BitmapFactory.decodeStream(im));
        return convertView;
    }

    public class ViewHolder {
        TextView tvLessonName;
        TextView tvLessonNumber;
        ImageView ivImage;
        ImageButton btnGame;
        ImageButton btnPronoun;
    }
}
