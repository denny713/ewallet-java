package id.privy.service;

import id.privy.entity.User;

import javax.transaction.Transactional;

@Transactional
public interface UserService {

    public void saveUser(User user);

    public User getByUsername(String username);

    public void deleteUser(User user);
}
