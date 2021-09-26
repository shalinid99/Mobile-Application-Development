package com.coffee_secrets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mReg;

    private EditText mLEmail;
    private EditText mLPw;

     private FirebaseAuth auth;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mLogin = findViewById(R.id.login);
        mReg = findViewById(R.id.reg);

        mLEmail = findViewById(R.id.l_email);
        mLPw = findViewById(R.id.l_pw);



        auth = FirebaseAuth.getInstance();




        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_emil = mLEmail.getText().toString();
                String txt_pw = mLPw.getText().toString();
                 loginUser(txt_emil ,txt_pw);

            }
        });


        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Login_2_Activity.class);
                startActivity(i);

            }
        });



    }

    private void loginUser(String emil, String pw) {

        auth.signInWithEmailAndPassword(emil ,pw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Home_Activity.class));
                finish();

            }
        });


    }
}