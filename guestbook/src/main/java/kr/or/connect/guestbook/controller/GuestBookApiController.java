package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.guestbook.dto.GuestBook;
import kr.or.connect.guestbook.service.GuestBookService;

@RestController
// 여기서 get, post, delete mapping으로 반환하면 전부 rest controller에 의해 json으로 변형되어서 클라이언트에게 갈 것이다. 라는게 가장 restApi에서의 핵심
@RequestMapping(path="/guestbooks")
public class GuestBookApiController {
	@Autowired
	GuestBookService guestBookService;
	
	@GetMapping
	public Map<String, Object> list(@RequestParam(name="start", required = false, defaultValue = "0")int start){
		List<GuestBook> list = guestBookService.getGuestBooks(start);
		
		int count = guestBookService.getCount();
		int pageCount = count / GuestBookService.LIMIT;
		if(count%GuestBookService.LIMIT>0) {
			pageCount++;
		}
		
		List<Integer> pageStartList = new ArrayList<Integer>();
		for (int i = 0; i < pageCount; i++) {
			pageStartList.add(i*guestBookService.LIMIT);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pageStartList", pageStartList);
		map.put("count", count);
		
		return map;
	}
	
	@PostMapping
	public GuestBook wirte(@RequestBody GuestBook guestBook, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		GuestBook result = guestBookService.addGuestBook(guestBook, clientIp);
		return result;
	}
	
	@DeleteMapping("/{id}")
	public Map<String, String>delete(@PathVariable(name="id")Long id, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		
		int deleteCount = guestBookService.deleteGuestBook(id, clientIp);
		return Collections.singletonMap("success", deleteCount>0 ? "true" : "false");
		
		
		
	}
}
