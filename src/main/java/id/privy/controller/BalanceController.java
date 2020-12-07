package id.privy.controller;

import id.privy.constant.Message;
import id.privy.entity.*;
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
                if (login.getUsername().equals(balance.getUsername())) {
                    try {
                        List<UserBalanceHistory> balanceHistory = new ArrayList<>();
                        Double total;
                        User user = userService.getByUsername(balance.getUsername());
                        if (user != null) {
                            UserBalance userBalance = balanceService.getByUser(user.getId());
                            UserBalanceHistory detail = new UserBalanceHistory();
                            detail.setActivity(balance.getActivity());
                            detail.setIp(InetAddress.getLocalHost().getHostAddress());
                            detail.setType(balance.getType());
                            detail.setLocation(balance.getLocation());
                            detail.setUserAgent(balance.getAgent());
                            detail.setAuthor(this.getUserLogin(request).getUsername());
                            if (userBalance != null) {
                                total = balance.getTotal() + userBalance.getBalance();
                                userBalance.setUserBalanceHistories(null);
                                detail.setBalanceBefore(userBalance.getBalance());
                                detail.setBalanceAfter(total);
                                balanceHistory.add(detail);
                                userBalance.setBalance(total);
                                userBalance.setAchieve(balance.getTotal());
                                userBalance.setUserBalanceHistories(balanceHistory);
                            } else {
                                userBalance = new UserBalance();
                                userBalance.setUserId(user.getId());
                                userBalance.setBalance(balance.getTotal());
                                userBalance.setAchieve(balance.getTotal());
                                detail.setBalanceBefore(0.0);
                                detail.setBalanceAfter(balance.getTotal());
                                balanceHistory.add(detail);
                                userBalance.setUserBalanceHistories(balanceHistory);
                            }
                            balanceService.saveUserBalance(userBalance);
                            response.setResult(true);
                            response.setMessage("Berhasil Top Up Saldo");
                        } else {
                            response.setResult(false);
                            response.setMessage("Username " + balance.getUsername() + " Tidak Ditemukan");
                        }
                    } catch (Exception k) {
                        response.setResult(false);
                        response.setMessage(k.getMessage());
                    }
                } else {
                    response.setResult(false);
                    response.setMessage("Tidak Bisa Top Up Dengan User Yang Berbeda");
                }
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
        }
        return response;
    }

    @PostMapping("/transfer")
    @ResponseBody
    public Response transferBalance(@Valid @RequestBody BalanceInput balance, HttpServletRequest request) {
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() == null) {
                response.setResult(false);
                response.setMessage(Message.UNLOGIN);
            } else {
                if (login.getUsername().equals(balance.getUsername())) {
                    try {
                        List<BankBalanceHistory> bankHistory = new ArrayList<>();
                        Double total;
                        Double selisih;
                        User user = userService.getByUsername(balance.getUsername());
                        if (user == null) {
                            response.setResult(false);
                            response.setMessage("Username " + balance.getUsername() + " Tidak Ditemukan");
                        } else {
                            UserBalance userBalance = balanceService.getByUser(user.getId());
                            if (userBalance == null) {
                                response.setResult(false);
                                response.setMessage("Tidak Ada Saldo Untuk Ditransfer");
                            } else {
                                Integer ids = 0;
                                for (int x = 0; x < userBalance.getUserBalanceHistories().size(); x++) {
                                    if (userBalance.getUserBalanceHistories().get(x).getUserBalanceId() > ids) {
                                        ids = userBalance.getUserBalanceHistories().get(x).getUserBalanceId();
                                    }
                                }
                                ids = ids + 1;
                                selisih = userBalance.getBalance() - balance.getTotal();
                                if (selisih < 0.0) {
                                    response.setResult(false);
                                    response.setMessage("Saldo Tidak Cukup Untuk Dilakukan Transfer");
                                } else {
                                    List<UserBalanceHistory> histories = new ArrayList<>();
                                    UserBalanceHistory userHistory = new UserBalanceHistory();
                                    userHistory.setId(userBalance.getId());
                                    userHistory.setUserBalanceId(ids);
                                    userHistory.setIdHistory("User" + userBalance.getId() + ids);
                                    userHistory.setActivity(balance.getActivity());
                                    userHistory.setBalanceBefore(userBalance.getBalance());
                                    userHistory.setBalanceAfter(selisih);
                                    userHistory.setIp(InetAddress.getLocalHost().getHostAddress());
                                    userHistory.setType("Credit");
                                    userHistory.setLocation(balance.getLocation());
                                    userHistory.setUserAgent(balance.getAgent());
                                    userHistory.setAuthor(this.getUserLogin(request).getUsername());
                                    histories.add(userHistory);
                                    userBalance.setUserBalanceHistories(histories);
                                    userBalance.setBalance(selisih);
                                    userBalance.setAchieve(balance.getTotal());
                                    BankBalanceHistory history = new BankBalanceHistory();
                                    history.setActivity(balance.getActivity());
                                    history.setAuthor(this.getUserLogin(request).getUsername());
                                    history.setIp(InetAddress.getLocalHost().getHostAddress());
                                    history.setLocation(balance.getLocation());
                                    history.setType(balance.getType());
                                    history.setUserAgent(balance.getAgent());
                                    BankBalance bankBalance = balanceService.getByUserId(user.getId());
                                    if (bankBalance == null) {
                                        bankBalance = new BankBalance();
                                        bankBalance.setAchieve(balance.getTotal());
                                        bankBalance.setBalance(balance.getTotal());
                                        bankBalance.setCode(balance.getCode());
                                        bankBalance.setEnable(true);
                                        bankBalance.setUserId(user.getId());
                                        history.setBalanceBefore(0.0);
                                        history.setBalanceAfter(balance.getTotal());
                                        bankHistory.add(history);
                                        bankBalance.setBankBalanceHistories(bankHistory);
                                    } else {
                                        total = bankBalance.getBalance() + balance.getTotal();
                                        history.setBalanceBefore(bankBalance.getBalance());
                                        history.setBalanceAfter(total);
                                        bankHistory.add(history);
                                        bankBalance.setBalance(total);
                                        bankBalance.setAchieve(balance.getTotal());
                                        bankBalance.setCode(balance.getCode());
                                        bankBalance.setBankBalanceHistories(bankHistory);
                                    }
                                    try {
                                        balanceService.saveBankBalance(bankBalance);
                                        balanceService.saveUserBalance(userBalance);
                                        response.setResult(true);
                                        response.setMessage("Berhasil Transfer Saldo");
                                    } catch (Exception f) {
                                        response.setResult(false);
                                        response.setMessage(f.getMessage());
                                    }
                                }
                            }
                        }
                    } catch (Exception j) {
                        response.setResult(false);
                        response.setMessage(j.getMessage());
                    }
                } else {
                    response.setResult(false);
                    response.setMessage("Tidak Bisa Transfer Dengan User Yang Berbeda");
                }
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
        }
        return response;
    }
}
