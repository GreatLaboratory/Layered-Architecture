package kr.or.connect.guestbook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LogInterceptor extends HandlerInterceptorAdapter {
// 이 인터셉터는 web~ config에다가 등록한다.
// 인터셉터의 역할은... 중간중간 콘솔창에다가 로그를 기록해서 잘 동작하는지를 알아보기위해 하는걸까..

	@Override
	// 말그대로 pre는 controller의 메소드가 수행되기 전에 실행되는 메소드
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(handler.toString() + "를 호출했습니다.");
		return true;
	}

	@Override
	// 말그대로 pre는 controller의 메소드가 수행되기 후에 실행되는 메소드
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(handler.toString() + "가 종료되었습니다." + modelAndView.getViewName() + "을 view로 사용합니다.");
	}
	 
}
