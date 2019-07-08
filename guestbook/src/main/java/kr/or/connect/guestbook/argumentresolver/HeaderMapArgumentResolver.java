package kr.or.connect.guestbook.argumentresolver;

import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class HeaderMapArgumentResolver implements HandlerMethodArgumentResolver {
//이렇게 만든 이 리졸버클래스를 적용하려면 web~ config클래스에다가 등록해야한다.
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// controller메소드의 인자가 4개일 경우에 이 메소드가 매번 호출이 된다.
		// 들어온 parameter변수에 headerinfo클래스가 들어오면 true를 리턴하도록 설정해놓음 
		// 여기서 true를 리턴해야지만 밑에 있는 resolveArgument메소드가 호출이 된다.
		return parameter.getParameterType()==HeaderInfo.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 여기서 return한 값은 controller메소드의 인자로 전달이 된다.
		
		HeaderInfo headerInfo = new HeaderInfo();
		
		Iterator<String> headerNames = webRequest.getHeaderNames();
		while(headerNames.hasNext()) {
			String headerName = headerNames.next();
			String headerValue = webRequest.getHeader(headerName);
			System.out.println(headerName + ", " + headerValue);
			headerInfo.put(headerName, headerValue);
		}
		return headerInfo;
	}

}
