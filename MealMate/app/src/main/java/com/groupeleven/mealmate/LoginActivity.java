package com.groupeleven.mealmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout loginFragment;
    TextView registerTV;
    TextView forgotPasswordTV;
    Button loginBtn;
    EditText emailET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpStatusbarColor();
        setUpViews();
        startAnimate();
    }

    private void startAnimate() {
        loginFragment.setY(-1700);
        loginFragment.animate().translationY(0).setDuration(400).setStartDelay(0);

        registerTV.setY(3000);
        registerTV.animate().translationY(0).setDuration(1100).setStartDelay(0);
    }

    private void setUpViews() {
        registerTV = findViewById(R.id.register_TV);
        loginBtn = findViewById(R.id.loginBtn);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginFragment = findViewById(R.id.loginFragment);
        forgotPasswordTV = findViewById(R.id.forgotPasswordTV);

        loginBtn.setOnClickListener(v -> loginPressed());

        registerTV.setOnClickListener(v -> registerClicked());
        forgotPasswordTV.setOnClickListener(view -> {
            forgotPasswordPressed();
        });
    }

    private void forgotPasswordPressed() {
        String userEnteredMail = emailET.getText().toString();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (userEnteredMail.length() != 0) {
            fbAuth.sendPasswordResetEmail(userEnteredMail).addOnSuccessListener(unused -> {
                Toast.makeText(this, "Password reset mail sent!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "User not found :(", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginPressed() {
        loginBtn.setText(R.string.validating);
        String userEnteredMail = emailET.getText().toString();
        String userEnteredPassWord = passwordET.getText().toString();
//
//        FirebaseFirestore fbdb = FirebaseFirestore.getInstance();
//        DocumentReference fbdr = fbdb.collection(FireBaseEntitites.USERS).document(userEnteredMail);
//
//        fbdr.get().addOnSuccessListener(documentSnapshot -> {
//            if (documentSnapshot.exists()) {
//                String passWord = documentSnapshot.getString(FireBaseEntitites.PASS);
//                if (passWord != null) {
//                    if (passWord.contentEquals(userEnteredPassWord)) {
//                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        passwordET.setText("");
//                        Toast.makeText(this, "Invalid credentials :(", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }).addOnFailureListener(e -> {
//
//        });

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        fbAuth.signInWithEmailAndPassword(userEnteredMail, userEnteredPassWord).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (fbAuth.getCurrentUser() != null) {
                    if (fbAuth.getCurrentUser().isEmailVerified()) {
                        SharedPreferences sp = getSharedPreferences("MealMateSharedPreferences", MODE_PRIVATE);
                        sp.edit().putBoolean("isSignedIn", true).apply();
                        Toast.makeText(this, "LOGIN SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Registration/Email verification pending.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerClicked() {
        Intent registerActivityIntent = new Intent(this, RegisterActivity.class);
        this.startActivity(registerActivityIntent);
    }

    private void setUpStatusbarColor() {
        Window appWindow = this.getWindow();
        appWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        appWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        appWindow.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loginFragment != null) {
            startAnimate();
        }
    }
}