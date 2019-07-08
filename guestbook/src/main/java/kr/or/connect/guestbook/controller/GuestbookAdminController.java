package kr.or.connect.guestbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GuestbookAdminController {
// 로그인폼을 요청하면 해당 요청을 처리

	@GetMapping(path = "/loginform")
	public String loginform() {
		// 따로 수행하는 메소드처리는 없고 그저 /loginform 이라고 url이 들어오면 loginform.jsp파일로 view정보를 보내주는 역할
		return "loginform";
	}

	@PostMapping(path = "/login")
	// jsp페이지에 form의 method방식이 post였으므로 매핑도 postmapping으로 url을 읽어온다.
	// 또한 jsp페이지에 form의 action태그가 login으로 되었으므로 /login으로 매핑된 url을 읽어온다.
	public String login(@RequestParam(name = "passwd", required = true) String passwd, 
			HttpSession session,
			RedirectAttributes redirectAttr) {	
		// 로그인메소드는 loginform으로부터 비밀번호를 전달받아서 암호가 일치할 경우에 세션에 로그인정보를 저장하는 목적
		
		if("1234".equals(passwd)) {
			session.setAttribute("isAdmin", "true");
			//관리자의 세션인지를 저장하기위해 이런식으로 설정 isAdmin이 true이면 관리자, false이면 비관리자
		} else {
			redirectAttr.addFlashAttribute("errorMessage", "암호가 틀렸습니다");
			// 이렇게 하면 redirect할 때 딱 한번만 에러메세지값을 유지하기위해 사용
			return "redirect:/loginform";
			
		}
		
		return "redirect:/list";
	}
}
