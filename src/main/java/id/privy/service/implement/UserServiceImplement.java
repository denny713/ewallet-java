package id.privy.service.implement;

import id.privy.entity.User;
import id.privy.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplement extends BaseService implements UserService {

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
