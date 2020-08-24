package id.privy.controller;

import com.google.gson.Gson;
import id.privy.entity.User;
import id.privy.model.Login;
import id.privy.model.Response;
import id.privy.model.UserLogin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@RestController
public class AccountController extends BaseController {

    private Response response = new Response();

    @PostMapping("/login")
    @ResponseBody
    public Response login(@Valid @RequestBody Login login, HttpSession session) throws NoSuchAlgorithmException {
        session.setAttribute("UserLogin", null);
        response = userService.checkLogin(login);
        if (response.getResult()) {
            User user = new Gson().fromJson(response.getMessage(), User.class);
            UserLogin userLogin = new UserLogin();
            userLogin.setUsername(user.getUsername());
            userLogin.setEmail(user.getEmail());
            userLogin.setStatus(user.getStatus());
            userLogin.setLogedAt(new Date());
            session.setAttribute("UserLogin", userLogin);
            session.setMaxInactiveInterval(-1);
            response.setMessage("Berhasil Login");
        }
        return response;
    }

    @PostMapping("/logout")
    @ResponseBody
    public Response logout(HttpServletRequest request, HttpSession session) {
        UserLogin login = this.getUserLogin(request);
        if (login.getUsername() == null) {
            response.setResult(false);
            response.setMessage("Anda Belum Login");
        } else {
            session.removeAttribute("UserLogin");
            response.setResult(true);
            response.setMessage("Berhasil Logout");
        }
        return response;
    }
}
