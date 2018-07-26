package com.example.webservice.web;

import com.example.webservice.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
	@Autowired
	private EmailSender emailSender;
	@Autowired
	UsersRepository usersRepository;

	public String sendEmailAction(String id, String what) throws Exception{
		Email email = new Email(); //이메일 객체 생성
		String subject="0";
		String content="0";
		if (what.equals("signup")) { // 회원가입
        		subject = "이메일 본인인증 for withus"; //이메일 제목
        		if (usersRepository.existsById(id) == true){ //id 있으면 회원가입 안되게 0반환
                		return "0";
            		}
        	}
        	else if (what.equals("changepw")){ // 비밀번호 변경
		 	subject = "임시 비밀번호 for withus";
            		if (usersRepository.existsById(id) == false){ //id 없으면 실패
                		return "0";
            		}
        	}
		String temp = RandomString.randomString(6); //랜덤 6자리

      		if (what.equals("signup")) {
            		content = "인증창에" + temp + "를 입력하세요";
        	}
        	else if (what.equals("changepw")){
			content ="회원님의 임시 비밀번호는 " + temp + "입니다.";
		    	String salt = RandomString.randomString(32); //솔트 생성
            		usersRepository.findById(id).get().setSalt(salt); //id찾아서 솔트 셋
            		usersRepository.findById(id).get().setSalt(Hashash.hundredTime(salt, temp.toString())); //id 찾아서 다이제스트 셋
            		usersRepository.save(usersRepository.findById(id).get()); //저장
        	}
		//이메일 
        	email.setReceiver (id);
        	email.setSubject(subject);
        	email.setContent(content);
        	emailSender.SendEmail(email);
        	return temp.toString();
	}
}
