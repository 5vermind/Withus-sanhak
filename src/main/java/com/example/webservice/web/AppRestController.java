package com.example.webservice.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class AppRestController {
	@Autowired
	BasicService basicService;
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;
	String temp;

	@RequestMapping("/basicinfo")
	public Map basicInfo(HttpServletRequest request){
		return basicService.basicInfo();
	}

	@RequestMapping("/cpugraphinfo")
	public List<Float> cpuGraphInfo(HttpServletRequest request){
		return basicService.cpuGraphInfo();
	}

	@RequestMapping("/memorygraphinfo")
	public List<Float> memoryGraphInfo(HttpServletRequest request){
		return basicService.memoryGraphInfo();
	}

	@RequestMapping("/helloworld")
	public String helloWorld(HttpServletRequest request){
		return "HelloWorld";
	}

	@RequestMapping("/login")
	public String logIn(HttpServletRequest request){
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		return userService.logIn(id, pw);
	}

	@RequestMapping("/signup")
    public String signUp(HttpServletRequest request){
	    String id = request.getParameter("id");
	    String pw = request.getParameter("pw");
        String certi = request.getParameter("cn");
        if (certi.equals(this.temp)) {
            return userService.signUp(id, pw);
        }
        else {
            return "0";
        }
    }

	@RequestMapping("/changePassword")
   	public String changePass(HttpServletRequest request){
	    String id = request.getParameter("id");
	    String cur_pw = request.getParameter("cur_pw");
	    String new_pw = request.getParameter("new_pw");
	    return userService.changePw(id, cur_pw, new_pw);
	}

	@RequestMapping("/goodBye")
    public String cancelid(HttpServletRequest request){
	    String id = request.getParameter("id");
	    String  pw = request.getParameter("pw");
	    String del = request.getParameter("request");
	    if (!id.equals("null") && del.equals("null")){
	        return userService.logIn(id, pw);
        }
        else if (!del.equals(null)){
            return userService.cancelId(id, pw);
        }
        else {
	        return "0";
        }
    }

    @RequestMapping("/send")
    public String sendEmail(HttpServletRequest request) throws Exception {
	    String id = request.getParameter("id");
	    String what = request.getParameter("what");
        this.temp = emailService.sendEmailAction(id, what);
        System.out.println(this.temp);
        if (this.temp.equals("0")){
            return "-1"; // 아이디가 이미 있을 때
        }
	    return "1";
    }
}

