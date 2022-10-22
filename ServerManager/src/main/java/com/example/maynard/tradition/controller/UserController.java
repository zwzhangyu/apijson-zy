package com.example.maynard.tradition.controller;

import com.example.maynard.tradition.entity.User;
import com.example.maynard.tradition.service.MessageService;
import com.example.maynard.tradition.service.UserService;
import com.example.maynard.tradition.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author zhangyu
 */

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/login")
    public Response login(@RequestParam("userName") String userName,
                          @RequestParam("userPass") String userPass,
                          HttpSession session) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return Response.no("用户名不存在！");
        }
        if (!Objects.equals(user.getUserPass(), userPass)) {
            return Response.no("密码不正确！");
        }
        session.setAttribute("user", user);
        return Response.yes(user);
    }


    @GetMapping("/logout")
    public Response logout(HttpSession session) {
        session.removeAttribute("user");
        return Response.yes();
    }

    @GetMapping("/current-user")
    public Response login(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.no("用户未登录");
        }
        Integer unReadSize = messageService.getUnReadSize(user.getUserId());
        user.setUnReadSize(unReadSize);
        return Response.yes(user);
    }


}
