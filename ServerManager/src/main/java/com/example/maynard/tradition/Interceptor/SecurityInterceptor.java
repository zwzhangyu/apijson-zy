package com.example.maynard.tradition.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        if (request.getSession().getAttribute("user") == null) {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            PrintWriter out = null;
//            try {
//                JSONObject res = new JSONObject();
//                res.put("success", false);
//                res.put("message", "用户未登录！");
//                out = response.getWriter();
//                out.append(res.toString());
//                return false;
//            } catch (Exception e) {
//                e.printStackTrace();
//                response.sendError(500);
//                return false;
//            }
//        }
        return true;
    }
}
