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

    private ProfileSetupPagerAdapater pagerAdapter;

    private ViewPager viewPager;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        pagerAdapter = new ProfileSetupPagerAdapater(getSupportFragmentManager());
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

    private class ProfileSetupPagerAdapater extends FragmentStatePagerAdapter {
        public ProfileSetupPagerAdapater(FragmentManager fm) {
            super(fm);
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
    }
}
