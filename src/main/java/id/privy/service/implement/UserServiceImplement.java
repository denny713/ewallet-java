package id.privy.service.implement;

import com.google.gson.Gson;
import id.privy.entity.User;
import id.privy.model.Login;
import id.privy.model.Response;
import id.privy.service.UserService;
import id.privy.util.Encrypt;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImplement extends BaseService implements UserService {

    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            user.setId(userRepository.getUserSequence());
        }
        userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Response checkLogin(Login login) throws NoSuchAlgorithmException {
        Response response = new Response();
        User user = userRepository.findByUsername(login.getUsername());
        if (user != null) {
            user = userRepository.findFirstByUsernameAndPassword(login.getUsername(), Encrypt.textEncrypt(login.getPassword()));
            if (user != null) {
                response.setResult(true);
                response.setMessage(new Gson().toJson(user));
            } else {
                response.setResult(false);
                response.setMessage("Username Atau Password Salah");
            }
        } else {
            response.setResult(false);
            response.setMessage("Data User " + login.getUsername() + " Tidak Ditemukan");
        }
        return response;
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
