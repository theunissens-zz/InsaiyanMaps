package za.co.insaiyan.budgeteer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import za.co.insaiyan.budgeteer.R;
import za.co.insaiyan.budgeteer.data.ProfileDAO;

/**
 * Created by Insaiyan on 10/23/2016.
 */

public class ProfileSpinnerAdapter extends BaseAdapter implements SpinnerAdapter{

    private Activity activity;
    private List<ProfileDAO> profiles;

        public ProfileSpinnerAdapter(Activity activity, List<ProfileDAO> profiles){
        this.activity = activity;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Object getItem(int i) {
        return profiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View spinView;
        if( view == null ){
            LayoutInflater inflater = activity.getLayoutInflater();
            spinView = inflater.inflate(R.layout.spinner_profile, null);
        } else {
            spinView = view;
        }
        TextView value = (TextView) spinView.findViewById(R.id.spinner_profile_value);
        value.setText(profiles.get(i).getName());
        return spinView;
    }

}
