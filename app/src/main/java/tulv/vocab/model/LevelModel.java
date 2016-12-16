package tulv.vocab.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import tulv.vocab.Entities.Level;

/**
 * Created by tulv on 9/14/2016.
 */

public class LevelModel extends DatabaseHelper {
    public LevelModel(Context context) {
        super(context);
    }
  public   ArrayList<Level> getList(){
        ArrayList<Level> arrayList=new ArrayList<>();
        String sql="select * from Level";
        Cursor cursor=this.getCursor(sql);
        while (cursor.moveToNext()){
            arrayList.add(new Level(cursor.getInt(0),cursor.getString(1),cursor.getBlob(2)));
        }
        return arrayList;
    }
}
