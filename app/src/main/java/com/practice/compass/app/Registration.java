package com.practice.compass.app;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.practice.compass.Validators.ContactNumberValidator;
import com.practice.compass.Validators.EmailValidator;
import com.practice.compass.Validators.PasswordValidator;
import com.practice.compass.login.R;
import com.practice.compass.network.request.SignUpRequest;
import com.practice.compass.network.request.SignUpResponse;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Registration extends BaseActivity implements View.OnClickListener{

    Button register;
    EditText nameField,emailField,passwordField,retypePasswordField,mobileNumberField;
    ProgressDialog registrationInProgress;
    String name,email,password,retypePassword,mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        nameField = (EditText) findViewById(R.id.register_name);
        emailField = (EditText) findViewById(R.id.register_email);
        passwordField = (EditText) findViewById(R.id.register_password);
        retypePasswordField = (EditText) findViewById(R.id.register_retype_password);
        mobileNumberField = (EditText) findViewById(R.id.register_contact_no);
        registrationInProgress = new ProgressDialog(this);
        registrationInProgress.setTitle("Sign Up");
        registrationInProgress.setMessage("Please Wait. Your registration is in progress.");
    }

    @Override
    public void onClick(View v) {

        name = nameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        retypePassword = retypePasswordField.getText().toString();
        mobileNumber = mobileNumberField.getText().toString();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);

        if(!(name.equals("") || email.equals("") || password.equals("") || retypePassword.equals("") || mobileNumber.equals(""))
                && password.equals(retypePassword)) {

            if(new EmailValidator().isInvalid(email)){
                emailField.setError("Invalid Email");
                return;
            }

            else if(new PasswordValidator().isInvalid(password)){
                passwordField.setError("Password can not be less than 6 characters");
                return;
            }

            else if(new ContactNumberValidator().isInvalid(mobileNumber)){
                mobileNumberField.setError("Invalid contact number");
                return;
            }

            SignUpRequest signUpRequest = new SignUpRequest(email,password,name,mobileNumber);
            registrationInProgress.show();
            getCommonSpiceManager().execute(signUpRequest,"Sign Up", DurationInMillis.ONE_SECOND*2, new SignUpRequestListener());

        }

        else{
            if(name.equals(""))  { Crouton.makeText(this, "Name can not be left blank", Style.ALERT).show(); }
            else if(name.equals(""))  { Crouton.makeText(this,"Name can not be left blank",Style.ALERT).show(); }
            else if(email.equals(""))  { Crouton.makeText(this,"Email can not be left blank",Style.ALERT).show(); }
            else if(password.equals(""))  { Crouton.makeText(this,"Password can not be left blank",Style.ALERT).show(); }
            else if(retypePassword.equals(""))  { Crouton.makeText(this,"ReType Password can not be left blank",Style.ALERT).show(); }
            else if(mobileNumber.equals(""))  { Crouton.makeText(this,"Contact Number can not be left blank",Style.ALERT).show(); }
            else if(!password.equals(retypePassword))  { Crouton.makeText(this,"Retype Password does not match with Password",Style.ALERT).show(); }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent logInIntent = new Intent(Registration.this,Login.class);
        startActivity(logInIntent);
        finish();
    }

    private class SignUpRequestListener implements RequestListener<SignUpResponse>{

        @Override
        public void onRequestFailure(SpiceException e) {
            registrationInProgress.dismiss();
            Crouton.makeText(Registration.this,"Username already exists.",Style.ALERT).show();
        }

        @Override
        public void onRequestSuccess(SignUpResponse signUpResponse) {
            registrationInProgress.dismiss();
            Intent logInIntent = new Intent(Registration.this, Login.class);
            logInIntent.putExtra("From Registration Successful",true);
            startActivity(logInIntent);
            finish();

        }
    }
}
