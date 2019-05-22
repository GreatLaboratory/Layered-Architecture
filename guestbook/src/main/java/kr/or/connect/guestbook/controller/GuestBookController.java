package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.guestbook.dto.GuestBook;
import kr.or.connect.guestbook.service.GuestBookService;

@Controller
public class GuestBookController {
	@Autowired
	GuestBookService guestBookService;
	
	@GetMapping(path = "/list")
	public String list(@RequestParam(name="start", required = false, defaultValue = "0")int start, ModelMap model) {
		//modelmap은 request스코프 대신 스프링에서 사용하는 정보 넘겨주기 객체
		
		// start로 시작하는 방명록 목록 구해오기
		List<GuestBook> list = guestBookService.getGuestBooks(start);
		
		// 전체 페이지 수 구하기
		int count = guestBookService.getCount();
		int pageCount = count/GuestBookService.LIMIT;
		
		if(count%GuestBookService.LIMIT >0) {
			pageCount++;
		}
		
		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<Integer>();
		for (int i = 0; i < pageCount; i++) {
			pageStartList.add(i*GuestBookService.LIMIT);
		}
		
		//jsp에서 사용하도록 model에다가 필요한 부분들 넣어주기.
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		
		return "list";
	}
	
	@PostMapping(path="/write")
	public String write(@ModelAttribute GuestBook guestBook, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
		guestBookService.addGuestBook(guestBook, clientIp);
		return "redirect:list";
		
		
		
	}
}
