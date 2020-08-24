package id.privy.controller;

import id.privy.entity.User;
import id.privy.model.Response;
import id.privy.model.UserLogin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController extends BaseController {

    private Response response = new Response();
    private UserLogin login = new UserLogin();

    @GetMapping("/user")
    @ResponseBody
    public List<Object> allUserBalance(HttpServletRequest request) {
        List<Object> hasil = new ArrayList<>();
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() != null) {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    hasil.add(balanceService.getUserBalance());
                } else {
                    User user = userService.getByUsername(login.getUsername());
                    if (user != null) {
                        hasil.add(balanceService.getByUser(user.getId()));
                    } else {
                        response.setResult(false);
                        response.setMessage("Username " + login.getUsername() + " Tidak Ditemukan");
                        hasil.add(response);
                    }
                }
            } else {
                response.setResult(false);
                response.setMessage("Anda Belum Login");
                hasil.add(response);
            }
        } else {
            response.setResult(false);
            response.setMessage("Anda Belum Login");
            hasil.add(response);
        }
        return hasil;
    }
}
