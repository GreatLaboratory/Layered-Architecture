package kr.or.connect.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.guestbook.dao.GuestBookDao;
import kr.or.connect.guestbook.dao.LogDao;
import kr.or.connect.guestbook.dto.GuestBook;
import kr.or.connect.guestbook.dto.Log;
import kr.or.connect.guestbook.service.GuestBookService;

@Service
public class GuestBookServiceImpl implements GuestBookService{
	@Autowired
	GuestBookDao guestBookDao;
	
	@Autowired
	LogDao logDao;

	@Override
	@Transactional
	// transactional 이거는 읽기 전용인 메소드에 붙여주면 되는데 내부적으로 readOnly라는 형태로 connection을 사용하게 된다.
	// transactional 이거는 이 메소드가 처음부터 끝까지 오류없이 성공되어야 진짜 다 시행되는거이다. 중간까지 되다가 중간에 막히면 그 전에 된것들 무효로 돌린다.
	public List<GuestBook> getGuestBooks(Integer start) {
		// 게스트북 목록 가져오기 메소드
		List<GuestBook> list = guestBookDao.selectAll(start, LIMIT);
		return list;
	}

	@Override
	@Transactional(readOnly = false )
	public int deleteGuestBook(Long id, String ip) {
		// 해당 id값의 방명록 삭제하기
		int deleteCount = guestBookDao.deleteById(id);
		
		//삭제 정보 로그에 남기기
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly = false )
	public GuestBook addGuestBook(GuestBook guestBook, String ip) {
		// 컨트롤러 단에서 클라이언트로부터 받은 방명록 정보를 guestBook으로 받아와서 직접 저장하는 메소드
		// 클라이언트로부터 받아올 때 날짜시간 값을 입력받아 가져오진 않았을 것이므로
		guestBook.setRegdate(new Date());
		Long id = guestBookDao.insert(guestBook);
		guestBook.setId(id);
		
		Log log = new Log();
		log.setIp(ip);
		log.setRegdate(new Date());
		log.setMethod("insert");
		logDao.insert(log);
		
		return guestBook;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return guestBookDao.selectCount();
	}
	
	
}
