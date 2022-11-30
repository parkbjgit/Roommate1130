package com.example.roommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;                 // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;             // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtName, mEtAge; // 회원가입 입력필드
    private Button mBtnRegister;                        // 회원가입 버튼
    private RadioGroup rb_gender;                       // 성별 그룹
    private RadioButton rb_man, rb_woman;               // 남성, 여성
    private String str_result;                          // 성별값 저장


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Roommate");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mEtName = findViewById(R.id.et_name);
        mEtAge = findViewById(R.id.et_age);
        rb_gender = findViewById(R.id.rg_gender);
        rb_man = findViewById(R.id.rb_man);
        rb_woman = findViewById(R.id.rb_woman);

        rb_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//라디오 버튼들의 상태 값의 변경을 감지
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rb_man) {
                    str_result = rb_man.getText().toString(); // 라디오 버튼의 text(남자)값을 String에 저장
                }else if(i == R.id.rb_woman){
                    str_result = rb_woman.getText().toString(); // 라디오 버튼의 text(여자)값을 String에 저장
                }
            }
        });


        mBtnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //빈칸이 하나라도 있을 시 Toast메세지 출력
                if(mEtEmail.getText().toString().length() == 0 || mEtPwd.toString().length() == 0 ||
                        mEtName.getText().toString().length() == 0 || str_result.toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "빈칸을 입력해 주십시오", Toast.LENGTH_SHORT).show();
                }
                else {
                    //회원가입 처리 시작
                    String strEmail = mEtEmail.getText().toString();
                    String strPwd = mEtPwd.getText().toString();
                    String strName = mEtName.getText().toString();
                    String strAge = mEtAge.getText().toString();
                    String strGen = str_result.toString();


                    //Firebase Auth 진행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setName(strName);
                                account.setAge((strAge));
                                account.setGen(strGen);


                                //setValue : DB에 insert
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                finish();//현재 액티비티 파괴
                            } else {
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
