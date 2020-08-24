package id.privy.controller;

import id.privy.constant.Message;
import id.privy.entity.User;
import id.privy.model.Response;
import id.privy.model.UserInput;
import id.privy.model.UserLogin;
import id.privy.util.Encrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private Response response = new Response();
    private UserLogin login = new UserLogin();

    @GetMapping("")
    @ResponseBody
    public List<Object> all(HttpServletRequest request) {
        List<Object> hasil = new ArrayList<>();
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() == null) {
                response.setResult(false);
                response.setMessage("Anda Belum Login");
                hasil.add(response);
            } else {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    hasil.addAll(userService.getAll());
                } else {
                    hasil.add(userService.getByUsername(login.getUsername()));
                }
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
            hasil.add(response);
        }
        return hasil;
    }

    @PostMapping("/save")
    @ResponseBody
    public Response saveUser(@Valid @RequestBody UserInput user) {
        try {
            User usr = new User();
            usr.setUsername(user.getUsername());
            usr.setPassword(Encrypt.textEncrypt(user.getPassword()));
            usr.setEmail(user.getEmail());
            usr.setStatus(user.getStatus());
            userService.saveUser(usr);
            response.setResult(true);
            response.setMessage("Berhasil Simpan Data");
        } catch (Exception d) {
            response.setResult(false);
            response.setMessage(d.getMessage());
        }
        return response;
    }

    @PostMapping("/update")
    @ResponseBody
    public Response updateUser(@Valid @RequestBody UserInput user, HttpServletRequest request) {
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() != null) {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    response = updateData(user);
                } else {
                    if (user.getUsername().equals(login.getUsername())) {
                        response = updateData(user);
                    } else {
                        response.setResult(false);
                        response.setMessage("Anda Tidak Berhak Mengubah Data User " + user.getUsername());
                    }
                }
            } else {
                response.setResult(false);
                response.setMessage(Message.UNLOGIN);
            }
        } else {
            response.setResult(false);
            response.setMessage(Message.UNLOGIN);
        }
        return response;
    }

    public Response updateData(UserInput user) {
        try {
            User usr = userService.getByUsername(user.getUsername());
            if (usr != null) {
                usr.setEmail(user.getEmail());
                usr.setPassword(Encrypt.textEncrypt(user.getPassword()));
                userService.saveUser(usr);
                response.setResult(true);
                response.setMessage("Berhasil Ubah Data");
            } else {
                response.setResult(false);
                response.setMessage("Username " + user.getUsername() + " Tidak Ditemukan");
            }
        } catch (Exception d) {
            response.setResult(false);
            response.setMessage(d.getMessage());
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response deleteUser(@PathVariable("id") String id, HttpServletRequest request) {
        if (isLogin(request)) {
            login = this.getUserLogin(request);
            if (login.getUsername() != null) {
                if (login.getStatus().equalsIgnoreCase("admin")) {
                    response = deleteUser(id);
                } else {
                    if (login.getUsername().equalsIgnoreCase(id)) {
                        response = deleteUser(id);
                    } else {
                        response.setResult(false);
                        response.setMessage("Anda Tidak Berhak Menghapus Data User " + id);
                    }
                }
            } else {
                response.setResult(false);
                response.setMessage("Anda Belum Login");
            }
        } else {
            response.setResult(false);
            response.setMessage("Anda Belum Login");
        }
        return response;
    }

    public Response deleteUser(String id) {
        try {
            User user = userService.getByUsername(id);
            if (user != null) {
                userService.deleteUser(user);
                response.setResult(true);
                response.setMessage("Berhasil Hapus Data");
            } else {
                response.setResult(false);
                response.setMessage("Username " + id + " Tidak Ditemukan");
            }
        } catch (Exception h) {
            response.setResult(false);
            response.setMessage(h.getMessage());
        }
        return response;
    }
}
