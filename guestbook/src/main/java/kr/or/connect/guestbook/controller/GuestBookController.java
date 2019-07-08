package kr.or.connect.guestbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.guestbook.argumentresolver.HeaderInfo;
import kr.or.connect.guestbook.dto.GuestBook;
import kr.or.connect.guestbook.service.GuestBookService;

@Controller
public class GuestBookController {
	@Autowired
	GuestBookService guestBookService;

	@GetMapping(path = "/list")
	public String list(@RequestParam(name = "start", required = false, defaultValue = "0") int start, ModelMap model,
			HeaderInfo headerInfo) {
		// modelmap은 request스코프 대신 스프링에서 사용하는 정보 넘겨주기 객체

		System.out.println("--------------------------------------");
		System.out.println(headerInfo.get("user-agent"));
		System.out.println("--------------------------------------");
		
		
		// start로 시작하는 방명록 목록 구해오기
		List<GuestBook> list = guestBookService.getGuestBooks(start);

		// 전체 페이지 수 구하기
		int count = guestBookService.getCount();
		int pageCount = count / GuestBookService.LIMIT;

		if (count % GuestBookService.LIMIT > 0) {
			pageCount++;
		}

		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<Integer>();
		for (int i = 0; i < pageCount; i++) {
			pageStartList.add(i * GuestBookService.LIMIT);
		}

		// jsp에서 사용하도록 model에다가 필요한 부분들 넣어주기.
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("list", list);
		model.addAttribute("count", count);

		return "list";
	}

	@PostMapping(path = "/write")
	public String write(@ModelAttribute GuestBook guestBook, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
		guestBookService.addGuestBook(guestBook, clientIp);
		return "redirect:list";
	}

	// 세션관련 메소드
	@GetMapping(path = "/delete")
	public String delete(@RequestParam(name = "id", required = true) Long id, // 여기서 name="id"는 url에서 delete?id=4일 때 이
																				// 숫자
			@SessionAttribute("isAdmin") String isAdmin, // 로그인할 때 세션속성으로 isAdmin했던거 -> "true"
			HttpServletRequest request, RedirectAttributes redirectAttr) {
		if (isAdmin == null || !isAdmin.equals("true")) {
			redirectAttr.addFlashAttribute("errorMessage", "로그인을 하지않았습니다.");
			return "redirect:/loginform";
		}
		String clientIp = request.getRemoteAddr();
		guestBookService.deleteGuestBook(id, clientIp);
		return "redirect:/list";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("isAdmin");
		return "redirect:/loginform";
	}

}
