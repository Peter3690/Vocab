package tulv.vocab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tulv.vocab.R;

/**
 * Created by tulv on 12/1/2016.
 */
public class Game1Adapter extends BaseAdapter {
    ArrayList<String> arrayList;
    Context context;
    public Game1Adapter(Context context, ArrayList<String> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
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
        String s = arrayList.get(position);
        ViewHolder viewHolder = null;
        // Khởi tạo view.
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.grid_item_game2,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvEnglish = (TextView) convertView.findViewById(R.id.tvABC);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvEnglish.setText(s);
        return convertView;
    }

    public class ViewHolder {
        TextView tvEnglish;
    }

}
