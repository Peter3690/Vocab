package tulv.vocab.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import tulv.vocab.Entities.Level;
import tulv.vocab.R;
import tulv.vocab.view.LessonActivity;

/**
 * Created by tulv on 10/18/2016.
 */
public class LevelFragment extends android.support.v4.app.Fragment {


    ArrayList<Level> arrayList;
    Level level;
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         int position = getArguments().getInt(EXTRA_POSITION);
        arrayList = new ArrayList<>();
        arrayList = getArguments().getParcelableArrayList("list");
        level = new Level();
        position=position%arrayList.size();
        level = arrayList.get(position);
        View v = inflater.inflate(R.layout.fragment_level, container, false);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        Button btInto = (Button) v.findViewById(R.id.btInto);
        btInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",level.getId());
                bundle.putString("name",level.getName());
                Intent intent = new Intent(getContext(), LessonActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
        byte[] blod=level.getImage();
       ByteArrayInputStream im=new ByteArrayInputStream(blod);
        img.setImageBitmap(BitmapFactory.decodeStream(im));
        return v;
    }
}