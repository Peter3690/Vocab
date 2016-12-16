package tulv.vocab.presenter.vocab;

import android.content.Context;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.model.VocabModel;

/**
 * Created by tulv on 9/16/2016.
 */
public class VocabPresenterImpl implements VocabPresenter {
    VocabModel vocabModel;
    public VocabPresenterImpl(Context context){
        vocabModel=new VocabModel(context);
    }
    @Override
    public ArrayList<Vocab> getList() {
        return vocabModel.getList();
    }

    @Override
    public ArrayList<Vocab> getListByLessonID(int id) {
        return vocabModel.getListByLessonID(id);
    }

    @Override
    public ArrayList<Vocab> getListRemind() {
        return vocabModel.getListRemind();
    }
}
