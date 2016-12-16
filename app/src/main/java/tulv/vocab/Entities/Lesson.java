package tulv.vocab.Entities;

/**
 * Created by tulv on 9/14/2016.
 */
public class Lesson {
    private int id;
    private String name;
    private byte[] image;
    private int levelId;

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Lesson(int id, int levelId, String name, byte[] image) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.levelId = levelId;
    }

    public Lesson() {
    }
}
