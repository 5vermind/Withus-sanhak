package com.example.webservice.web;

import com.example.webservice.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import  java.sql.*;


@Service
public class EmailController {
	@Autowired
	private EmailSender emailSender;
	@Autowired
	UsersRepository usersRepository;

	public String sendEmailAction(String id, String what) throws Exception{
		Email email = new Email();
		String subject="0";
		String content="0";
		if (what.equals("signup")) {
            subject = "이메일 본인인증 for withus";
            if (usersRepository.existsById(id) == true){
                return "0";
            }
        }
        else if (what.equals("changepw")){
		    subject = "임시 비밀번호 for withus";
            if (usersRepository.existsById(id) == false){
                return "0";
            }
        }
		String temp = RandomString.randomString(6);

        if (what.equals("signup")) {
            content = "인증창에" + temp + "를 입력하세요";
        }
        else if (what.equals("changepw")){
		    content ="회원님의 임시 비밀번호는 " + temp + "입니다.";
		    String salt = RandomString.randomString(32);
            usersRepository.findById(id).get().setSalt(salt);
            usersRepository.findById(id).get().setSalt(Hashash.hundredTime(salt, temp.toString()));
            usersRepository.save(usersRepository.findById(id).get());
        }
        email.setReceiver (id);
        email.setSubject(subject);
        email.setContent(content);
        emailSender.SendEmail(email);
        return temp.toString();
	}
}
