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
    LayoutInflater inflater;
    // Hàm tạo của custom
    public Game1Adapter(Context context, ArrayList<String> arrayList) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList=arrayList;
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
        // Khai báo các component
        TextView tvABC;
        // Khởi tạo view.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_game, parent, false);
        }
        tvABC = (TextView) convertView.findViewById(R.id.tvABC);
        tvABC.setText(s);
        return convertView;
    }
}
