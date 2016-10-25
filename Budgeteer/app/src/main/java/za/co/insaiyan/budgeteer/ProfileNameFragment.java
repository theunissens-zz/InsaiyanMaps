package za.co.insaiyan.budgeteer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import za.co.insaiyan.budgeteer.data.FixedIncomeDAO;
import za.co.insaiyan.budgeteer.data.ProfileDAO;
import za.co.insaiyan.budgeteer.handler.ProfileManager;

public class ProfileNameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Realm realm;

    public ProfileNameFragment() {
        // Required empty public constructor
    }

    public static ProfileNameFragment newInstance() {
        ProfileNameFragment fragment = new ProfileNameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_name, container, false);

        final EditText profileName = (EditText)view.findViewById(R.id.editText_profile_name);

        Button buttonNextProfileName = (Button)view.findViewById(R.id.button_profile_name_next);
        final ProfileSetupActivity psActivity = (ProfileSetupActivity)getActivity();
        buttonNextProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProfileNameFragment.this.realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        ProfileDAO profile = realm.createObject(ProfileDAO.class);
                        profile.setName(profileName.getText().toString());
                        ProfileManager.getInstance().setProfileLoaded(profile);
                    }
                });

                psActivity.incrementCount();
                psActivity.getViewPager().setCurrentItem(psActivity.getCount(), true);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
