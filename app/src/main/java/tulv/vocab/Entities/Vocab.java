package tulv.vocab.Entities;
;import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tulv on 9/14/2016.
 */
public class Vocab implements Parcelable {
    private int id;
    private String english;
    private String vietnamese;
    private byte[] image;
    private String pronoun;
    private boolean remind;
    private int lessonId;

    protected Vocab(Parcel in) {
        id = in.readInt();
        english = in.readString();
        vietnamese = in.readString();
        image = in.createByteArray();
        pronoun = in.readString();
        remind = in.readByte() != 0;
        lessonId = in.readInt();
    }

    public static final Creator<Vocab> CREATOR = new Creator<Vocab>() {
        @Override
        public Vocab createFromParcel(Parcel in) {
            return new Vocab(in);
        }

        @Override
        public Vocab[] newArray(int size) {
            return new Vocab[size];
        }
    };

    public void setId(int id){
        this.id=id;
    }

    public void setEnglish(String english){
        this.english=english;
    }

    public void setVietnamese(String vietnamese){
        this.vietnamese=vietnamese;
    }

    public void setPronoun(String pronoun){
        this.pronoun=pronoun;
    }

    public void setImage(byte[] image){
        this.image=image;
    }

    public void setRemind(boolean remind){
        this.remind=remind;
    }

    public void setLessonId(int lessonId){
        this.lessonId=lessonId;
    }

    public int getId(){
        return id;
    }

    public String getEnglish(){
        return english;
    }

    public String getVietnamese(){
        return vietnamese;
    }

    public String getPronoun(){
        return pronoun;
    }

    public byte[] getImage(){
        return image;
    }

    public int getLessonId(){return lessonId;}

    public boolean getRemind(){
        return remind;
    }

    public Vocab(){}

    public Vocab(int id, String english, String vietnamese, String pronoun, byte[] image, boolean remind, int lessonId){
        this.id=id;
        this.english=english;
        this.vietnamese=vietnamese;
        this.pronoun=pronoun;
        this.image=image;
        this.remind=remind;
        this.lessonId=lessonId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(english);
        parcel.writeString(vietnamese);
        parcel.writeByteArray(image);
        parcel.writeString(pronoun);
        parcel.writeByte((byte) (remind ? 1 : 0));
        parcel.writeInt(lessonId);
    }
}
