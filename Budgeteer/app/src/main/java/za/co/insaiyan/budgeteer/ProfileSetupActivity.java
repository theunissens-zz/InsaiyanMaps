package za.co.insaiyan.budgeteer;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

public class ProfileSetupActivity extends AppCompatActivity implements ProfileNameFragment.OnFragmentInteractionListener, ProfileSetupFixedIncomeFragment.OnFragmentInteractionListener, ProfileSetupFixedExpenseFragment.OnFragmentInteractionListener {

    private ProfileSetupPagerAdapter pagerAdapter;

    private ViewPager viewPager;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        pagerAdapter = new ProfileSetupPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void incrementCount() {
        this.count++;
    }

    public int getCount() {
        return this.count;
    }

    public ViewPager getViewPager() {
        return this.viewPager;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProfileNameUpdated(String profileName) {
        ProfileSetupFixedIncomeFragment profileSetupFixedIncomeFragment =  (ProfileSetupFixedIncomeFragment)pagerAdapter.getFragmentManager().getFragments().get(1);
        profileSetupFixedIncomeFragment.update();
    }

    private class ProfileSetupPagerAdapter extends FragmentStatePagerAdapter {
        private FragmentManager fm;
        public ProfileSetupPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                Fragment fragment = ProfileNameFragment.newInstance();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                return fragment;
            } else if (i == 1) {
                Fragment fragment = ProfileSetupFixedIncomeFragment.newInstance();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                return fragment;
            } else if (i == 2) {
                Fragment fragment = ProfileSetupFixedExpenseFragment.newInstance();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                return fragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0 : return "Setup Profile Name";
                case 1 : return "Setup Fixed Income";
                case 2 : return "Setup Fixed Expenses";
                default:return"";
            }
        }

        public FragmentManager getFragmentManager() {
            return fm;
        }
    }
}
