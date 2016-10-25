package za.co.insaiyan.budgeteer;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import za.co.insaiyan.budgeteer.adapters.ProfileSetupFixedIncomeAdapter;
import za.co.insaiyan.budgeteer.data.FixedIncomeDAO;
import za.co.insaiyan.budgeteer.data.ProfileDAO;
import za.co.insaiyan.budgeteer.handler.ProfileManager;

public class ProfileSetupFixedIncomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Realm realm;

    private ProfileSetupFixedIncomeAdapter adapter;
    private RecyclerView recyclerView;

    public ProfileSetupFixedIncomeFragment() {
    }

    public static ProfileSetupFixedIncomeFragment newInstance() {
        ProfileSetupFixedIncomeFragment fragment = new ProfileSetupFixedIncomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.realm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_setup_fixed_income, container, false);

        setupRecyclerView(view);

        addListeners(view);

        return view;
    }

    public void update() {
        this.adapter = new ProfileSetupFixedIncomeAdapter(this, ProfileManager.getInstance().getIncomeItems(this.realm));
        recyclerView.setAdapter(this.adapter);
    }

    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list_fixed_income);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void addListeners(View view) {

        // Floating action button
        FloatingActionButton addFixedIncomeButton = (FloatingActionButton)view.findViewById(R.id.fab_add_fixed_income_item);
        addFixedIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View promptsView = inflater.inflate(R.layout.dialog_add_fixed_income, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setView(promptsView);

                final EditText fixedIncomeName = (EditText) promptsView.findViewById(R.id.editText_add_fixed_income_name);
                final EditText fixedIncomeAmount = (EditText) promptsView.findViewById(R.id.editText_add_fixed_income_amount);
                final EditText fixedIncomeDate = (EditText) promptsView.findViewById(R.id.editText_add_fixed_income_date);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ProfileSetupFixedIncomeFragment.this.realm.executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                // Create new income item
                                                FixedIncomeDAO income = realm.createObject(FixedIncomeDAO.class);
                                                income.setName(fixedIncomeName.getText().toString());
                                                String strFixedIncomeAmount = fixedIncomeAmount.getText().toString();
                                                income.setAmount(Double.parseDouble(strFixedIncomeAmount));
                                                income.setDate(fixedIncomeDate.getText().toString());
                                                // Get profile to add the new income item
                                                ProfileDAO profile = realm.where(ProfileDAO.class).equalTo("name", ProfileManager.getInstance().getProfileLoaded()).findFirst();
                                                profile.getIncomeItems().add(income);
                                            }
                                        });
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });

        // Next button
        Button buttonNextProfileName = (Button)view.findViewById(R.id.button_profile_income_next);
        final ProfileSetupActivity psActivity = (ProfileSetupActivity)getActivity();
        buttonNextProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                psActivity.incrementCount();
                psActivity.getViewPager().setCurrentItem(psActivity.getCount(), true);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
