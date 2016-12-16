package tulv.vocab.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import tulv.vocab.Entities.Level;
import tulv.vocab.fragment.LevelFragment;

/**
 * Created by tulv on 10/18/2016.
 */
public class LevelPageAdapter extends FragmentStatePagerAdapter {


    ArrayList<Level> arrayList;

    public LevelPageAdapter(android.support.v4.app.FragmentManager fm,ArrayList<Level> arrayList) {
        super(fm);
        this.arrayList=arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        final Bundle bundle = new Bundle();
        bundle.putInt(LevelFragment.EXTRA_POSITION, position);
        bundle.putParcelableArrayList("list",arrayList);
        final LevelFragment fragment = new LevelFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return arrayList.size()*100;
    }
}