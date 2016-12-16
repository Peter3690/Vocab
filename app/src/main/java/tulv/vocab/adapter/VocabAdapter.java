package tulv.vocab.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import tulv.vocab.Entities.Vocab;
import tulv.vocab.R;
import tulv.vocab.fragment.VocabFragment;
import tulv.vocab.layout.VocabLayout;

/**
 * Created by tulv on 10/19/2016.
 */
public class VocabAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.8f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    VocabFragment vocabFragment=new VocabFragment();
    private Context context;
    private FragmentManager fm;
    private float scale;
    ArrayList<Vocab> arrayList;
    int PAGES;
    int FIRST_PAGE;

    public VocabAdapter(Context context, FragmentManager fm, ArrayList<Vocab> arrayList, int PAGES, int FIRST_PAGE) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.arrayList = arrayList;
        this.PAGES = PAGES;
        this.FIRST_PAGE = FIRST_PAGE;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == this.FIRST_PAGE)
            scale = BIG_SCALE;
        else
            scale = SMALL_SCALE;
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list",arrayList);
        bundle.putInt("pos",position);
        bundle.putFloat("scale",scale);
        final VocabFragment fragment = new VocabFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount()
    {
        return this.PAGES * 1000;
    }

    @Override
    public void transformPage(View page, float position) {
        VocabLayout myLinearLayout = (VocabLayout) page.findViewById(R.id.root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        myLinearLayout.setScaleBoth(scale);
    }
}