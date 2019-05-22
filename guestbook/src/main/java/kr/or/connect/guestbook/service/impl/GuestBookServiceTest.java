package kr.or.connect.guestbook.service.impl;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.dto.GuestBook;
import kr.or.connect.guestbook.service.GuestBookService;

public class GuestBookServiceTest {

	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		GuestBookService guestBookService = ac.getBean(GuestBookService.class);
		
		GuestBook guestBook = new GuestBook();
		guestBook.setName("youu-min");
		guestBook.setContent("nice to meet tuotuotuo");
		guestBook.setRegdate(new Date());
		GuestBook result = guestBookService.addGuestBook(guestBook, "127.0.0.1");
		System.out.println(result);
	}

}
