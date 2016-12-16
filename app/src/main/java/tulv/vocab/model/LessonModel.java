package tulv.vocab.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import tulv.vocab.Entities.Lesson;

/**
 * Created by tulv on 9/14/2016.
 */
public class LessonModel extends DatabaseHelper {
    public LessonModel(Context context) {
        super(context);
    }
    public ArrayList<Lesson> getList(){
        ArrayList<Lesson> arrayList=new ArrayList<>();
        String sql="select * from Lesson";
        Cursor cursor=this.getCursor(sql);
        while (cursor.moveToNext()){
            arrayList.add(new Lesson(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getBlob(3)));
        }
        return arrayList;
    }

    public ArrayList<Lesson> getListByLevelId(int id){
        ArrayList<Lesson> arrayList=new ArrayList<>();
        String sql="select * from Lesson where LevelId="+id+"";
        Cursor cursor=this.getCursor(sql);
        while (cursor.moveToNext()){
            arrayList.add(new Lesson(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getBlob(3)));
        }
        return arrayList;
    }
    public int countVocab(int id){
        String sql="select * from Vocab where LessonId="+id+"";
        Cursor cursor=this.getCursor(sql);
        int count=cursor.getCount();
        return count;
    }
}
