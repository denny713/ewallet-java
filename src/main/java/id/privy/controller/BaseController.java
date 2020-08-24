package id.privy.controller;

import com.google.gson.Gson;
import id.privy.model.UserLogin;
import id.privy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {

    @Autowired
    protected UserService userService;

    protected void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int x = 0; x < cookies.length; x++) {
                cookies[x].setValue("");
                cookies[x].setPath("");
                response.addCookie(cookies[x]);
            }
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setPath("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    protected Boolean setSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int x = 0; x < cookies.length; x++) {
                String json = cookies[x].getValue();
                UserLogin login = new Gson().fromJson(json, UserLogin.class);
                request.getSession().setAttribute("UserLogin", login);
                request.getSession().setMaxInactiveInterval(1);
            }
        }
        return false;
    }

    protected boolean isLogin(HttpServletRequest request) {
        UserLogin user = getUserLogin(request);
        try {
            if (!user.getUsername().equals("") || user.getUsername() != null) {
                return true;
            }
        } catch (Exception h) {
            return false;
        }
        return false;
    }

    protected UserLogin getUserLogin(HttpServletRequest request) {
        try {
            UserLogin login = (UserLogin) request.getSession().getAttribute("UserLogin");
            if (login == null) {
                return new UserLogin();
            }
            return login;
        } catch (Exception l) {
            return new UserLogin();
        }
    }
}
