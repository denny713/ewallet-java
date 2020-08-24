package id.privy.controller;

import id.privy.entity.BankBalance;
import id.privy.entity.User;
import id.privy.entity.UserBalance;
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
    private List<Object> hasil = new ArrayList<>();

    @GetMapping("/user")
    @ResponseBody
    public List<Object> allUserBalance(HttpServletRequest request) {
        hasil = new ArrayList<>();
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() != null) {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    List<UserBalance> userBalances = balanceService.getUserBalance();
                    if (userBalances.isEmpty()) {
                        response.setResult(false);
                        response.setMessage("Tidak Ada Transaksi User");
                        hasil.add(response);
                    } else {
                        hasil.addAll(userBalances);
                    }
                } else {
                    User user = userService.getByUsername(login.getUsername());
                    if (user != null) {
                        UserBalance balanceUser = balanceService.getByUser(user.getId());
                        if (balanceUser != null) {
                            hasil.add(balanceUser);
                        } else {
                            response.setResult(false);
                            response.setMessage("Tidak Ada Transaksi Ditemukan Untuk User " + login.getUsername());
                            hasil.add(response);
                        }
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

    @GetMapping("/bank")
    @ResponseBody
    public List<Object> allBankBalance(HttpServletRequest request) {
        hasil = new ArrayList<>();
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() != null) {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    List<BankBalance> bankBalances = balanceService.getBankBalance();
                    if (bankBalances.isEmpty()) {
                        response.setResult(false);
                        response.setMessage("Tidak Ada Transaksi Bank");
                        hasil.add(response);
                    } else {
                        hasil.addAll(bankBalances);
                    }
                } else {
                    User user = userService.getByUsername(login.getUsername());
                    if (user != null) {
                        BankBalance balanceBank = balanceService.getByUserId(user.getId());
                        if (balanceBank != null) {
                            hasil.add(balanceBank);
                        } else {
                            response.setResult(false);
                            response.setMessage("Tidak Ada Transaksi Ditemukan Untuk User " + login.getUsername());
                            hasil.add(response);
                        }
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
