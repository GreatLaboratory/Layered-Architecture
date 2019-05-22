package kr.or.connect.guestbook.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.guestbook.config.ApplicationConfig;
import kr.or.connect.guestbook.dto.GuestBook;

public class GuestBookDaoTest {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		GuestBookDao dao = ac.getBean(GuestBookDao.class);

		GuestBook book = new GuestBook();
		
//		dao.deleteById((long) 2);
		book.setName("myung-gwan");
		book.setContent("hi, hello");
		book.setRegdate(new Date());
		Long id = dao.insert(book);
		System.out.println("id : " + id);
	}
}
