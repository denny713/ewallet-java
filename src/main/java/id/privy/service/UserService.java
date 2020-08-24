package id.privy.service;

import id.privy.entity.User;
import id.privy.model.Login;
import id.privy.model.Response;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Transactional
public interface UserService {

    public void saveUser(User user);

    public User getByUsername(String username);

    public void deleteUser(User user);

    public Response checkLogin(Login login) throws NoSuchAlgorithmException;

    public List<User> getAll();
}
