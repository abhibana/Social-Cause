package com.practice.compass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.practice.compass.Validators.EmailValidator;
import com.practice.compass.Validators.PasswordValidator;
import com.practice.compass.login.R;
import com.practice.compass.network.request.LogInResponse;
import com.practice.compass.network.request.LoginRequest;

import java.io.IOException;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Login extends BaseActivity{

    Button register,logIn;
    EditText emailField,passwordField;
    CheckBox rememberMe;
    ProgressDialog logInProgress;
    private String email,password;
    private static final String PREFS_NAME = "Credentials";
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(getIntent().getBooleanExtra("From Registration Successful",false)){
            Crouton.makeText(Login.this, "Registration Successful", Style.CONFIRM).show();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        register = (Button) findViewById(R.id.login_register);
        logIn = (Button) findViewById(R.id.log_in);
        emailField = (EditText) findViewById(R.id.login_email);
        passwordField = (EditText) findViewById(R.id.login_password);
        rememberMe = (CheckBox) findViewById(R.id.remeber);
        logInProgress = new ProgressDialog(this);
        logInProgress.setTitle("Log In");
        logInProgress.setMessage("Please Wait. We are logging you to your account.");
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    public void doRegistration(View v){

        Intent registerIntent = new Intent(Login.this,Registration.class);
        startActivity(registerIntent);
        finish();
    }

    public void logIn(View v) throws IOException {
       performLogIn();
//        email = "abhishek@yopmail.com";
//        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
//        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("email",email);
//        editor.putString("password",password);
//        editor.commit();
////        Intent homeIntent = new Intent(Login.this, Home.class);
//        Intent homeIntent = new Intent(Login.this, Home.class);
//        homeIntent.putExtra("From Login",true);
//        finish();
//        startActivity(homeIntent);
    }

    private void performLogIn(){

        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);

        if(!(email.equals("") || password.equals(""))) {

            if(new EmailValidator().isInvalid(email)){
                emailField.setError("Invalid Email");
                return;
            }

            else if(new PasswordValidator().isInvalid(password)){
                Crouton.makeText(Login.this,"Invalid password",Style.ALERT);
                return;
            }

            LoginRequest logInRequest = new LoginRequest(email, password);
            logInProgress.show();
            logInProgress.setCancelable(false);
            getCommonSpiceManager().execute(logInRequest, "Login Call", DurationInMillis.ONE_SECOND * 2, new LogInRequestListener());
        }

        else if(email.equals("")) { Crouton.makeText(this,"Email Can not be left blank", Style.ALERT).show(); }

        else if(password.equals("")) { Crouton.makeText(this,"Password Can not be left blank",Style.ALERT).show(); }
    }

    private final class LogInRequestListener implements RequestListener<LogInResponse> {

        @Override
        public void onRequestFailure(SpiceException e) {
            logInProgress.hide();
            Crouton.makeText(Login.this, "Invalid Username or Password", Style.ALERT).show();
        }

        @Override
        public void onRequestSuccess(LogInResponse logInResponse) {

            logInProgress.hide();
            logInProgress.dismiss();

            SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email",email);
            editor.putString("password",password);
            if(rememberMe.isChecked()){
                editor.putBoolean("Remember",true);
            }
            editor.commit();
            Intent homeIntent = new Intent(Login.this, Home.class);
            homeIntent.putExtra("From Login",true);
            finish();
            startActivity(homeIntent);

        }
    }
}