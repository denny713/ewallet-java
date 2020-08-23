package id.privy.controller;

import id.privy.entity.User;
import id.privy.model.Response;
import id.privy.model.UserInput;
import id.privy.util.Encrypt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private Response response = new Response();

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
            response.setMessage("Success Save User");
        } catch (Exception d) {
            response.setResult(false);
            response.setMessage(d.getMessage());
        }
        return response;
    }

    @PostMapping("/update")
    @ResponseBody
    public Response updateUser(@Valid @RequestBody UserInput user) {
        try {
            User usr = userService.getByUsername(user.getUsername());
            if (usr != null) {
                usr.setEmail(user.getEmail());
                usr.setPassword(Encrypt.textEncrypt(user.getPassword()));
                userService.saveUser(usr);
                response.setResult(true);
                response.setMessage("Success Update Data");
            } else {
                response.setResult(false);
                response.setMessage("Username " + user.getUsername() + " Not Found");
            }
        } catch (Exception d) {
            response.setResult(false);
            response.setMessage(d.getMessage());
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response deleteUser(@PathVariable("id") String id) {
        try {
            User user = userService.getByUsername(id);
            if (user != null) {
                userService.deleteUser(user);
                response.setResult(true);
                response.setMessage("Success Delete Data");
            } else {
                response.setResult(false);
                response.setMessage("Username " + id + " Not Found");
            }
        } catch (Exception h) {
            response.setResult(false);
            response.setMessage(h.getMessage());
        }
        return response;
    }
}
