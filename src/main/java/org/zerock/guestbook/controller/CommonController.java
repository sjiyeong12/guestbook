package org.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 스프링부트를 쓸 때는 컨트롤러를 써줘야함
public class CommonController {
    @RequestMapping({ "", "/" }) // 주소 들고오기. get과 post를 합침.. get은 임시변수, post 임시공간
    public String index() { // 스트링은 주소. 나머지는 객체나 숫자를 리턴
        return "index";
    }
}
