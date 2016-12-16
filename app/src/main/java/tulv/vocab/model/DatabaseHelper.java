package tulv.vocab.model;

/**
 * Created by tulv on 10/17/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by tulv on 8/31/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private String DB_PATH = "data/data/tulv.vocab/databases/";
    private static String DB_NAME = "studyvocab.sqlite";
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        boolean check = checkDatabase();
        if (check) {
            openDatabase();
        } else {
            createDatabase();
        }
    }

    private void createDatabase() {
        boolean check = checkDatabase();
        if (check) {
        } else {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
        }
    }

    private void openDatabase() {
        sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDatabase() {
        boolean check = false;
        try {
            File file = new File(DB_PATH + DB_NAME);
            check = file.exists();
        } catch (Exception e) {
        }
        return check;
    }


    public int getCount(String sql)
    {
        openDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        int i=cursor.getCount();
        closeDatabase();
        return i;
    }

    public Cursor getCursor(String sql){
        openDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public void execSQL(String sql){
        openDatabase();
        sqLiteDatabase.execSQL(sql);
        closeDatabase();
    }

    private synchronized void closeDatabase() {
        if (sqLiteDatabase!=null)
        {
            sqLiteDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
