package org.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        log.info("register post...");
        Long gno = gbService.register(dto);
        ra.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

}
