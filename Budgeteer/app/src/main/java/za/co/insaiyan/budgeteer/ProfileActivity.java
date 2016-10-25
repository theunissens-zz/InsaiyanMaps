package za.co.insaiyan.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import za.co.insaiyan.budgeteer.adapters.ProfileSpinnerAdapter;
import za.co.insaiyan.budgeteer.data.ProfileDAO;
import za.co.insaiyan.budgeteer.handler.ProfileManager;

public class ProfileActivity extends AppCompatActivity {

    private Realm realm;

    private Spinner profileSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup Realm
        setupRealm();

        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupProfileList();

        addListeners();
    }

    private void setupProfileList() {
        profileSpinner = (Spinner) findViewById(R.id.spinner_profiles);
        RealmResults<ProfileDAO> profileResults = realm.where(ProfileDAO.class).findAll();
        ProfileSpinnerAdapter spinnerArrayAdapter = new ProfileSpinnerAdapter(this, profileResults);
        profileSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void addListeners() {
        Button buttonCreateProfile = (Button)findViewById(R.id.button_create_profile);
        buttonCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, ProfileSetupActivity.class);
                ProfileActivity.this.startActivity(myIntent);
            }
        });

        Button buttonLoadProfile = (Button)findViewById(R.id.button_load_profile);
        buttonLoadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileManager.getInstance().setProfileLoaded(((ProfileDAO) profileSpinner.getSelectedItem()).getName());
                Intent myIntent = new Intent(ProfileActivity.this, BudgeteerMainActivity.class);
                ProfileActivity.this.startActivity(myIntent);
            }
        });
    }

    private void setupRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        this.realm = Realm.getDefaultInstance();
    }

}
