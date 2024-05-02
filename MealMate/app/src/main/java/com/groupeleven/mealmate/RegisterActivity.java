package com.groupeleven.mealmate;

import static com.groupeleven.mealmate.FireBaseEntitites.EMAIL_ID;
import static com.groupeleven.mealmate.FireBaseEntitites.PASS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    RelativeLayout registerFragment;
    Button registerBtn;
    EditText nameET;
    EditText emailET;
    EditText passwordET;

    private FirebaseFirestore fbdb = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpStatusbarColor();

        registerFragment = findViewById(R.id.registerFragment);
        registerFragment.setY(-1700);
        registerFragment.animate().translationY(0).setDuration(400).setStartDelay(0);

        setUpViews();
    }

    private void setUpViews() {
        registerBtn = findViewById(R.id.registerBtn);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPressed();
            }
        });
    }

    private void registerPressed() {
        String nameEntered = nameET.getText().toString();
        String emailEntered = emailET.getText().toString();
        String passwordEntered = passwordET.getText().toString();

        if (!emailEntered.contains("@")) {
            Toast.makeText(this, "Email Invalid", Toast.LENGTH_SHORT).show();
        }
        else if (passwordEntered.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth fbAuth = FirebaseAuth.getInstance();

            fbAuth.createUserWithEmailAndPassword(emailEntered, passwordEntered).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (fbAuth.getCurrentUser() != null) {
                        fbAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(this, "Registration complete. Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                                Map<String, Object> entry = new HashMap<>();
                                entry.put(FireBaseEntitites.NAME, nameEntered);
                                entry.put(EMAIL_ID, emailEntered);
                                entry.put(PASS, passwordEntered);

                                fbdb.collection(FireBaseEntitites.USERS).document(emailEntered).set(entry);

                                Intent loginActivity = new Intent(this, LoginActivity.class);
                                this.startActivity(loginActivity);
                            } else {
                                Toast.makeText(this, "Verification email could not be sent :(", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Could not create user:( ", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void setUpStatusbarColor() {
        Window appWindow = this.getWindow();
        appWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        appWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        appWindow.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }
}