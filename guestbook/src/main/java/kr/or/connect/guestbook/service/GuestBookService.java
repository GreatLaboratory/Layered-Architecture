package kr.or.connect.guestbook.service;

import java.util.List;

import kr.or.connect.guestbook.dto.GuestBook;

public interface GuestBookService {
	public static final Integer LIMIT = 5;
	public List<GuestBook> getGuestBooks(Integer start);
	public int deleteGuestBook(Long id, String ip);
	public GuestBook addGuestBook(GuestBook guestBook, String ip);
	public int getCount();
}
