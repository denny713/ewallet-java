package id.privy.service.implement;

import id.privy.entity.UserBalance;
import id.privy.service.BalanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImplement extends BaseService implements BalanceService {

    @Override
    public List<UserBalance> getUserBalance() {
        return userBalanceRepository.findAll();
    }

    @Override
    public UserBalance getByUser(Integer userId) {
        return userBalanceRepository.findByUserId(userId);
    }
}
