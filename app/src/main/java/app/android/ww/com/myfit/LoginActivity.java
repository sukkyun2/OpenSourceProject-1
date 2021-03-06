package app.android.ww.com.myfit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 프로그램명 : LoginActivity
 * 작성자 : 홍석균
 * 작성일 : 2020.06.08
 * 프로그램 설명 :
 * 맨 처음 어플을 실행시에 나오는 로그인 화면입니다.
 * 아이디(or이메일)와 비밀번호를 입력하고 로그인이나 회원가입을 누르면
 * 회원정보가 FireBase RealTime DataBase에 저장됩니다.
 **/

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase=firebaseDatabase.getReference();

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button); // sign in round_button
        Button mEmailSignUpButton = findViewById(R.id.email_sign_up_button); // sign up round_button

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=mEmailView.getText().toString();
                LoginUtil.USERID=id;

                mDatabase.child("users").child(id).child("userId").setValue(id);

                Toast.makeText(getApplicationContext(), LoginUtil.USERID+"님 환영합니다.", Toast.LENGTH_LONG).show();

                /** 일시적으로 운동하기로 감 **/
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(intent);
                finish();

//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        mEmailSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id=mEmailView.getText().toString();
                LoginUtil.USERID=id;

                mDatabase.child("users").child(id).child("userId").setValue(id);

                Toast.makeText(getApplicationContext(), LoginUtil.USERID+"님 환영합니다.", Toast.LENGTH_LONG).show();

                /** 일시적으로 운동하기로 감 **/
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(intent);
                finish();

//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

}

