package org.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("guestbook")
@Log4j2
@AllArgsConstructor
public class GuestbookController {
    private GuestbookService gbService;

    @GetMapping({ "/" })
    public String index() {
        log.info("index.................");
        return "redirect:/guestbook/list";
    }

    @GetMapping({ "/list" })
    public void list(PageRequestDTO requestDTO, Model model) {
        log.info("list.................");
        model.addAttribute("result", gbService.getList(requestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("regist......");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes ra) {
        // RedirectAttributes는 다음 페이지로 값을 넘겨줄 수 있다. model처럼..!
        log.info("register post...");
        Long gno = gbService.register(dto);
        ra.addFlashAttribute("msg", gno + " 등록");
        // 플래시처럼 데이터를 한번만 전송해준다. 한번만 넘겨주고 다음에 넘겨줄 때는 못 받게 만들어 줌(일회성) ("메세지", 글번호)
        return "redirect:/guestbook/list";
        // redirect:를 붙인 이유 -> 32행의 값을 넘겨줘야함. 하지만 코드가 중복되는 것을 방지해야함.
        // 그리고 "/guestbook/list"이렇게만 적으면 리소스라서 값을 못 받음. 그래서 리소스가 아니라 주소로 보내야 하기 때문에
        // redirect:를 붙임
    }

    @GetMapping({ "/read", "/modify" })
    public void read(long gno, Model model,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        // 커맨드 객체로 받은 것은 다음 페이지에도 넘어감.
        log.info("read..........gno:" + gno);
        GuestbookDTO dto = gbService.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes ra,
            PageRequestDTO requestDTO) {
        log.info("remove....." + gno);
        gbService.remove(gno);
        ra.addFlashAttribute("msg", gno + " 삭제");
        ra.addAttribute("page", requestDTO.getPage());
        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modifyPost(GuestbookDTO dto, RedirectAttributes ra,
            PageRequestDTO requestDTO) {
        log.info("modify...." + dto);
        gbService.modify(dto);
        ra.addFlashAttribute("msg", dto.getGno() + " 수정");
        ra.addAttribute("gno", dto.getGno());
        ra.addAttribute("page", requestDTO.getPage());
        return "redirect:/guestbook/read";
    }

}
