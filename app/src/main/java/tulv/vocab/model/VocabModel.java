package tulv.vocab.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;

/**
 * Created by tulv on 9/14/2016.
 */
public class VocabModel extends DatabaseHelper {
    public VocabModel(Context context) {
        super(context);
    }

    public ArrayList<Vocab> getList(){
        ArrayList<Vocab> arrayList=new ArrayList<>();
        String sql="select * from Vocab";
        Cursor cursor=this.getCursor(sql);
        while (cursor.moveToNext()){
            arrayList.add(new Vocab(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getBlob(4),cursor.getWantsAllOnMoveCalls(),cursor.getInt(6)));
        }
        return arrayList;
    }

    public ArrayList<Vocab> getListByLessonID(int id){
        ArrayList<Vocab> arrayList=new ArrayList<>();
        String sql="select * from Vocab where LessonID="+id+"";
        Cursor cursor=this.getCursor(sql);
        while (cursor.moveToNext()){
            int b=cursor.getInt(5);
            boolean c=false;
            if (b==1){
                c=true;
            }
            arrayList.add(new Vocab(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getBlob(4),c,cursor.getInt(6)));
        }
        return arrayList;
    }

    public ArrayList<Vocab> getListRemind(){
        ArrayList<Vocab> arrayList=new ArrayList<>();
        String sql="select * from Vocab where Remind=1";
        Cursor cursor=this.getCursor(sql);
        while (cursor.moveToNext()){
            arrayList.add(new Vocab(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getBlob(4),cursor.getWantsAllOnMoveCalls(),cursor.getInt(6)));
        }
        return arrayList;
    }

    public void updateRemind(int id) {
        Vocab vocab = getVocab(id);
        boolean remind = vocab.getRemind();
        String sql = "update Vocab set Remind=1 where id=" + id + "";
        if (remind == true) {
            sql = "update Vocab set Remind=0 where id=" + id + "";
        }
        this.execSQL(sql);
    }

    public Vocab getVocab(int id) {
        String sql = "select * from Vocab where id=" + id + "";
        Cursor cursor = this.getCursor(sql);
        cursor.moveToFirst();
        int remind = cursor.getInt(5);
        boolean re = false;
        if (remind == 1) {
            re = Boolean.parseBoolean("true");
        }
        Vocab vocab = new Vocab(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4), re, cursor.getInt(6));
        return vocab;
    }
}
