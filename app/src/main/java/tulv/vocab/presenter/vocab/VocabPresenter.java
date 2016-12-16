package tulv.vocab.presenter.vocab;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;

/**
 * Created by tulv on 9/16/2016.
 */
public interface VocabPresenter {
    ArrayList<Vocab> getList();
    ArrayList<Vocab> getListByLessonID(int id);
    ArrayList<Vocab> getListRemind();
}
