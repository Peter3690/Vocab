package tulv.vocab.presenter.lesson;

import java.util.ArrayList;

import tulv.vocab.Entities.Lesson;

/**
 * Created by tulv on 9/16/2016.
 */
public interface LessonPresenter {
    ArrayList<Lesson> getList();

    ArrayList<Lesson> getListByLevelId(int id);
}
