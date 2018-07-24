package com.example.webservice.web;


import com.example.webservice.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.webservice.data.Hashash.hundredTime;


@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;

    public String logIn(String id, String pw){ //로그인 메소드
       if (usersRepository.existsById(id) == true){
           String digest = hundredTime(usersRepository.findById(id).get().getSalt(), pw);
           if(digest.equals(usersRepository.findById(id).get().getDigest())){
               return "1";
           }
           else { return "0"; }
       }
       else { return "0"; }
    }
    public String signUp(String id, String pw) { //사인업 메소드
        if (usersRepository.existsById(id) == false){
            String salt = RandomString.randomString(32);
            String digest = hundredTime(salt, pw);
            Users users = new Users();
            users.setId(id);
            users.setSalt(salt);
            users.setDigest(digest);
            usersRepository.save(users);
            return "1";
        }
        else{ return "0"; }

    }
    public String changePw(String id, String cur_pw, String new_pw){
        if (logIn(id, cur_pw) == "1"){
            String salt = RandomString.randomString(32);
            String digest = hundredTime(salt, new_pw);
            usersRepository.findById(id).get().setSalt(salt);
            usersRepository.findById(id).get().setDigest(digest);
            usersRepository.save(usersRepository.findById(id).get());
            return "1";
        }
        else { return "0"; }
    }
    public String cancelId(String id, String pw){
        if(logIn(id, pw) == "1"){
            usersRepository.delete(usersRepository.findById(id).get());
            return "1";
        }
        else{ return "0"; }
    }
}
