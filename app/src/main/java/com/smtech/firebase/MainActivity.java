package com.smtech.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.smtech.firebase.activities.LoginActivity;
import com.smtech.firebase.fragment.DashboardFragment;
import com.smtech.firebase.fragment.HomeFragment;
import com.smtech.firebase.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private HomeFragment mHomeFragment;
    private DashboardFragment mDashboardFragment;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        getSupportActionBar().setTitle("Tabed With Firebase SignUp");

        if (mAuth.getCurrentUser() != null) {

            BottomNavigationView mainBottomNav = findViewById(R.id.mainBottomNav);

            mHomeFragment = new HomeFragment();
            mDashboardFragment = new DashboardFragment();
            mProfileFragment = new ProfileFragment();

            initializeFragment();

            mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

                    switch (menuItem.getItemId()) {

                        case R.id.bottom_action_home:

                            replaceFragment(mHomeFragment, currentFragment);
                            return true;

                        case R.id.bottom_action_dashboard:

                            replaceFragment(mDashboardFragment, currentFragment);
                            return true;

                        case R.id.bottom_action_profile:

                            replaceFragment(mProfileFragment, currentFragment);
                            return true;

                        default:
                            return false;
                    }
                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            sendToLogin();
        } else {
            String current_user_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        if (!task.getResult().exists()) {

                            //...
                        }
                    } else {

                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout_btn:
                logOut();
                return true;

            default:
                return false;
        }
    }

    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void replaceFragment(Fragment fragment, Fragment currentFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment == mHomeFragment) {
            fragmentTransaction.hide(mDashboardFragment);
            fragmentTransaction.hide(mProfileFragment);
        }

        if (fragment == mDashboardFragment) {
            fragmentTransaction.hide(mHomeFragment);
            fragmentTransaction.hide(mProfileFragment);
        }

        if (fragment == mProfileFragment) {
            fragmentTransaction.hide(mHomeFragment);
            fragmentTransaction.hide(mDashboardFragment);
        }

        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    private void initializeFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main_container, mHomeFragment);
        fragmentTransaction.add(R.id.main_container, mDashboardFragment);
        fragmentTransaction.add(R.id.main_container, mProfileFragment);

        fragmentTransaction.hide(mDashboardFragment);
        fragmentTransaction.hide(mProfileFragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
