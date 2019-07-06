package kr.or.connect.guestbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuessNumberController {
	// 어떤 매핑으로 들어왔을 때 이 메소드를 실행할지
	@GetMapping(path ="/guess")
	public String guess(@RequestParam(name="number", required= false) Integer number, HttpSession session, ModelMap map) {
		// 위와 같이 requestparam어노테이션 사용하고 괄호에다가 저렇게 쓰면 jsp파일에 input name이 number인 input태그에서의 값을 가져오겠다는 뜻이 된다.
		// 그리고 그 값을 Integer타입의 number변수에다가 할당하겠다는 뜻
		// 스프링에선 request.getSession()같은 메소드없이 그냥 httpsession session이라고 선언만 해도 자동으로 생성된다.
		
		String message = null;
		if(number == null) {
			// 처음 요청이 들어왔을 때
			
			session.setAttribute("count", 0);
			session.setAttribute("randomNumber", (int)(Math.random()*100)+1);
			message = "내가 생각한 숫자를 맞춰보세요.";
		} else {
			// 사용자가 한번이라도 값을 입력해서 제출했을 때
			
			int count = (Integer)session.getAttribute("count");
			int randomNumber = (Integer)session.getAttribute("randomNumber");
			
			if(number < randomNumber) {
				message= "입력한 값은 너무 작습니다.";
				session.setAttribute("count", ++count);
			} else if(number > randomNumber) {
				message= "입력한 값은 너무 큽니다.";
				session.setAttribute("count", ++count);
			}else {
				message = ++count + "회 시도만에 정답입니다.";
				session.removeAttribute("count");
				session.removeAttribute("randomNumber");
			}
		}
		
		map.addAttribute("message", message);
		
		
		
		// 뷰리졸버에다가 넣을 스트링값 이렇게 guess를 반환하게끔하면 /guess.jsp url매핑으로 가게되된다.
		// 값을 입력한 후에 다시 그 페이지로 간다는 뜻
		return "guess";
		
		
	}
}
