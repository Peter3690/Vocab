package tulv.vocab.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by tulv on 12/5/2016.
 */
public class ReadWriteFile {
    Context context;

    public ReadWriteFile(Context context) {
        this.context = context;
    }

    public String readData(int id, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(in));
            String data = "";
            while ((data = reader.readLine()) != null) {
                String mang[] = data.split("#");
                if (Integer.parseInt(mang[0].toString()) == id) {
                    return mang[1].toString();
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<String> readAll(String fileName) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileInputStream in = context.openFileInput(fileName);
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(in));
            String data = "";
            while ((data = reader.readLine()) != null) {
                arrayList.add(data);
            }
            in.close();
            return arrayList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    /**
     * Hàm ghi tập tin trong Android
     * dùng openFileOutput để ghi
     * openFileOutput tạo ra FileOutputStream
     */
    public void writeData(int id, String fileName, String data) {
        String s = "";
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList = readAll(fileName);
            FileOutputStream out =
                    context.openFileOutput(fileName, context.MODE_PRIVATE);
            OutputStreamWriter writer =
                    new OutputStreamWriter(out);
            for (int i = 0; i < arrayList.size(); i++) {
                String mang[] = arrayList.get(i).split("#");
                if (Integer.parseInt(mang[0].toString()) == id) {

                } else {
                    s = s + "" + arrayList.get(i) + "\n";
                }
            }
            s = s + "" + id + "#" + data + "\n";
            writer.write(s);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
