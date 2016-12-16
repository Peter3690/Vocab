package tulv.vocab.presenter.level;

import android.content.Context;

import java.util.ArrayList;

import tulv.vocab.Entities.Level;
import tulv.vocab.model.LevelModel;

/**
 * Created by tulv on 12/1/2016.
 */
public class LevelPresenterImpl implements LevelPresenter {
    LevelModel levelModel;

    public LevelPresenterImpl(Context context) {
        this.levelModel = new LevelModel(context);
    }

    public ArrayList<Level> getList() {
        return levelModel.getList();
    }
}
