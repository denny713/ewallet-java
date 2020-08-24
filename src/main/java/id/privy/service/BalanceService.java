package id.privy.service;

import id.privy.entity.UserBalance;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BalanceService {

    public List<UserBalance> getUserBalance();

    public UserBalance getByUser(Integer userId);
}
