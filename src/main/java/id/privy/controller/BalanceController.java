package id.privy.controller;

import id.privy.constant.Message;
import id.privy.entity.BankBalance;
import id.privy.entity.User;
import id.privy.entity.UserBalance;
import id.privy.entity.UserBalanceHistory;
import id.privy.model.BalanceInput;
import id.privy.model.Response;
import id.privy.model.UserLogin;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
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
                response.setMessage(Message.UNLOGIN);
                hasil.add(response);
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
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
                response.setMessage(Message.UNLOGIN);
                hasil.add(response);
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
            hasil.add(response);
        }
        return hasil;
    }

    @PostMapping("/topup")
    @ResponseBody
    public Response topUpBalance(@Valid @RequestBody BalanceInput balance, HttpServletRequest request) {
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() == null) {
                response.setResult(false);
                response.setMessage(Message.UNLOGIN);
            } else {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    response = topUp(balance, request);
                } else {
                    if (login.getUsername().equals(balance.getUsername())) {
                        response = topUp(balance, request);
                    } else {
                        response.setResult(false);
                        response.setMessage("Tidak Bisa Top Up Dengan User Yang Berbeda");
                    }
                }
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
        }
        return response;
    }

    public Response topUp(BalanceInput balance, HttpServletRequest request) {
        try {
            List<UserBalanceHistory> balanceHistory = new ArrayList<>();
            Double total;
            User user = userService.getByUsername(balance.getUsername());
            if (user != null) {
                UserBalance userBalance = balanceService.getByUser(user.getId());
                if (userBalance != null) {
                    total = balance.getTotal() + userBalance.getBalance();
                    userBalance.setBalance(total);
                    userBalance.setAchieve(balance.getTotal());
                    userBalance.setUserBalanceHistories(new ArrayList<>());
                    UserBalanceHistory detail = new UserBalanceHistory();
                    detail.setActivity(balance.getActivity());
                    detail.setBalanceBefore(0.0);
                    detail.setBalanceAfter(balance.getTotal());
                    detail.setIp(InetAddress.getLocalHost().getHostAddress());
                    detail.setType(balance.getType());
                    detail.setLocation(balance.getLocation());
                    detail.setUserAgent(balance.getAgent());
                    detail.setAuthor(this.getUserLogin(request).getUsername());
                    balanceHistory.add(detail);
                    userBalance.setUserBalanceHistories(balanceHistory);
                } else {
                    userBalance = new UserBalance();
                    userBalance.setUserId(user.getId());
                    userBalance.setBalance(balance.getTotal());
                    userBalance.setAchieve(balance.getTotal());
                    UserBalanceHistory history = new UserBalanceHistory();
                    history.setActivity(balance.getActivity());
                    history.setBalanceBefore(0.0);
                    history.setBalanceAfter(balance.getTotal());
                    history.setIp(InetAddress.getLocalHost().getHostAddress());
                    history.setType(balance.getType());
                    history.setLocation(balance.getLocation());
                    history.setUserAgent(balance.getAgent());
                    history.setAuthor(this.getUserLogin(request).getUsername());
                    balanceHistory.add(history);
                    userBalance.setUserBalanceHistories(balanceHistory);
                }
                balanceService.saveUserBalance(userBalance);
                response.setResult(true);
                response.setMessage("Berhasil Top Up");
            } else {
                response.setResult(false);
                response.setMessage("Username " + balance.getUsername() + " Tidak Ditemukan");
            }
        } catch (Exception k) {
            response.setResult(false);
            response.setMessage(k.getMessage());
        }
        return response;
    }
}
