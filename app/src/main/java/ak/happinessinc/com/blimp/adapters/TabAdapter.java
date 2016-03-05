package ak.happinessinc.com.blimp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import ak.happinessinc.com.blimp.Clickfragment;
import ak.happinessinc.com.blimp.GalleryFragment;

/**
 * Created by Ananthakrishna on 03-03-2016.
 */
public  class TabAdapter extends FragmentStatePagerAdapter {
    int numberOfTabs=0;
    public TabAdapter(FragmentManager fm,int noOftabs) {
        super(fm);
        this.numberOfTabs=noOftabs;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new Clickfragment();
            case 1:
                return new GalleryFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
    private String[] tabs = { "Click", "Gallery" };
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

}
