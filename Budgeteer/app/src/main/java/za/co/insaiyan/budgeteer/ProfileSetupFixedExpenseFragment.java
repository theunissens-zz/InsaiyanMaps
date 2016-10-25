package za.co.insaiyan.budgeteer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import za.co.insaiyan.budgeteer.adapters.ProfileSetupFixedExpenseAdapter;
import za.co.insaiyan.budgeteer.data.FixedExpenseDAO;
import za.co.insaiyan.budgeteer.data.ProfileDAO;
import za.co.insaiyan.budgeteer.handler.ProfileManager;

public class ProfileSetupFixedExpenseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Realm realm;

    private ProfileSetupFixedExpenseAdapter adapter;

    public ProfileSetupFixedExpenseFragment() {

    }

    public static ProfileSetupFixedExpenseFragment newInstance() {
        ProfileSetupFixedExpenseFragment fragment = new ProfileSetupFixedExpenseFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_setup_fixed_expense, container, false);

        setupRecyclerView(view);

        addListeners(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_fixed_expense);

        this.adapter = new ProfileSetupFixedExpenseAdapter(this, this.realm.where(FixedExpenseDAO.class).findAllAsync());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this.adapter);
    }

    private void addListeners(View view)  {

        // Floating action button
        FloatingActionButton addFixedExpenseButton = (FloatingActionButton)view.findViewById(R.id.fab_add_fixed_expense_item);
        addFixedExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View promptsView = inflater.inflate(R.layout.dialog_add_fixed_expense, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setView(promptsView);

                final EditText fixedExpenseName = (EditText) promptsView.findViewById(R.id.editText_add_fixed_expense_name);
                final EditText fixedExpenseAmount = (EditText) promptsView.findViewById(R.id.editText_add_fixed_expense_amount);
                final EditText fixedExpenseDate = (EditText) promptsView.findViewById(R.id.editText_add_fixed_expense_date);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ProfileSetupFixedExpenseFragment.this.realm.executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                FixedExpenseDAO expense = realm.createObject(FixedExpenseDAO.class);
                                                expense.setName(fixedExpenseName.getText().toString());
                                                String strFixedExpenseAmount = fixedExpenseAmount.getText().toString();
                                                expense.setAmount(Double.parseDouble(strFixedExpenseAmount));
                                                expense.setDate(fixedExpenseDate.getText().toString());
                                                expense.setProfileName(ProfileManager.getInstance().getProfileLoaded().getName());
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

        // Done button
        Button buttonNextProfileName = (Button)view.findViewById(R.id.button_profile_setup_done);
        buttonNextProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), BudgeteerMainActivity.class);
                ProfileSetupFixedExpenseFragment.this.startActivity(myIntent);
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
