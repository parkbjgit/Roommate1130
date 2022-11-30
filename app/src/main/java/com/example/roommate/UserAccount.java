package com.example.roommate;

// 사용자 계정 정보 모델 클래스
public class UserAccount {

    private String idToken; // Firebase Uid(고유 토큰 정보)
    private String EmailId; // 이메일 아이디
    private String password;  // 비밀번호
    private String Name; //이름
    private String Age; //나이
    private String Gen; //성별

    public UserAccount() { }//빈 생성자 만들기

    //모델클래스에 입력 출력을 하기위한 설정
    //Alt+insert

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGen() {
        return Gen;
    }

    public void setGen(String gen) {
        Gen = gen;
    }
}
