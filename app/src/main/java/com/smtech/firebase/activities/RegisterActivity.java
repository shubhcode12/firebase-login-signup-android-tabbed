package com.smtech.firebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smtech.firebase.MainActivity;
import com.smtech.firebase.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmailField;
    private EditText registerPasswordField;
    private EditText registerConfirmPasswordField;

    private Button registerButton;
    private Button loginButton;
    private CalendarView calendarView;

    private ProgressBar registerProgress;

    private FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        registerEmailField = findViewById(R.id.reg_email);
        registerPasswordField = findViewById(R.id.reg_password);
        registerConfirmPasswordField = findViewById(R.id.reg_confirm_password);

        registerButton = findViewById(R.id.reg_btn);
        loginButton = findViewById(R.id.login_btn);

        registerProgress = findViewById(R.id.reg_progress);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = registerEmailField.getText().toString();
                String password = registerPasswordField.getText().toString();
                String confirm_password = registerConfirmPasswordField.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_password)) {

                    if (password.equals(confirm_password)) {

                        registerProgress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {

                                    sendToMainActivity();
                                    finish();

                                } else {

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                                }

                                registerProgress.setVisibility(View.INVISIBLE);

                            }
                        });

                    } else {

                        Toast.makeText(RegisterActivity.this, "Password fields doesn't match", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            sendToMainActivity();

        }
    }

    private void sendToMainActivity() {

        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
