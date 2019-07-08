package kr.or.connect.guestbook.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.or.connect.guestbook.argumentresolver.HeaderMapArgumentResolver;
import kr.or.connect.guestbook.interceptor.LogInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "kr.or.connect.guestbook.controller", "kr.or.conncet.guestbook.argumentresolver",
		"kr.or.connect.guestbook.interceptor" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	// 이 클래스는 dispatcher서블릿이 읽어들이는 것이라는 사실을 절대 잊으면 안된다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// url요청이 /css/**, /img/**, /js/** 이런 식으로 이루어지면 뒤에 있는 폴더에서 읽어오도록 설정하는 것이다.
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// url매핑이 없이 요청되어졌을 때 default servlet handler를 사용할 수 있게 해주는 메소드
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 특정 url에 대한 처리를 컨트롤러 클래스를 작성하지 않고 매핑할 수 있도록 해주는 메소드
		System.out.println("addViewControllers가 호출됩니다. ");
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		System.out.println("리졸버 등록....");
		argumentResolvers.add(new HeaderMapArgumentResolver());
	}

}
