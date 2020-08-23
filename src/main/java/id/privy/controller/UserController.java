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

    public Response response = new Response();

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
}
