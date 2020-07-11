package com.example.dialogflowapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// Don't be afraid to try something new
public class MainActivity extends AppCompatActivity {
    private Button       loginButton;
    private FirebaseAuth firebaseAuth;
    private EditText     email;
    private EditText     password;

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setViews();
        setUpButtonClick();
    }

    public boolean checkInput(){
        if(email.getText().toString().trim().isEmpty() &&
           password.getText().toString().trim().isEmpty()){
            emailLayout.setError("Field cannot be empty");
            passwordLayout.setError("Field cannot be empty");
            return false;
        }

        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            emailLayout.setError("Please enter a valid email address");
            passwordLayout.setErrorEnabled(false);
            return false;
        }

        else if(email.getText().toString().trim().isEmpty()){
            emailLayout.setError("Field cannot be empty");
            passwordLayout.setErrorEnabled(false);
            return false;
        }

        else if(password.getText().toString().trim().isEmpty()){
            passwordLayout.setError("Field cannot be empty");
            emailLayout.setErrorEnabled(false);
            return false;
        }

        else{
            emailLayout.setError(null);
            passwordLayout.setError(null);
            return true;
        }
    }

    public void setViews(){
        loginButton    = findViewById(R.id.loginButton);
        email          = findViewById(R.id.email);
        password       = findViewById(R.id.password);
        emailLayout    = findViewById(R.id.email_TextInputLayout);
        passwordLayout = findViewById(R.id.password_TextInputLayout);
    }

    public void setUpButtonClick(){
        loadingDialog = new LoadingDialog(MainActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    loadingDialog.startLoadingDialog();
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingDialog.dismissDialog();
                                goToHome();
                            } else{
                                loadingDialog.dismissDialog();
                                Snackbar.make(v, "Failed to login", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void goToHome(){
        Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finishAffinity();   // removes all activities from the backstack
    }
}