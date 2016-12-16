package tulv.vocab.presenter.lesson;

import android.content.Context;

import java.util.ArrayList;

import tulv.vocab.Entities.Lesson;
import tulv.vocab.model.LessonModel;

/**
 * Created by tulv on 9/16/2016.
 */
public class LessonPresenterImpl implements LessonPresenter {
    LessonModel lessonModel;
    public LessonPresenterImpl(Context context){
        lessonModel=new LessonModel(context);
    }
    @Override
    public ArrayList<Lesson> getList() {
        return lessonModel.getList();
    }

    @Override
    public ArrayList<Lesson> getListByLevelId(int id) {
        return lessonModel.getListByLevelId(id);
    }
}
