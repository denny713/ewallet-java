package id.privy.service.implement;

import id.privy.entity.BankBalance;
import id.privy.entity.UserBalance;
import id.privy.entity.UserBalanceHistory;
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

    @Override
    public List<BankBalance> getBankBalance() {
        return bankBalanceRepository.findAll();
    }

    @Override
    public BankBalance getByUserId(Integer userId) {
        return bankBalanceRepository.findByUserId(userId);
    }

    @Override
    public void saveUserBalance(UserBalance userBalance) {
        Integer id = 0;
        if (userBalance.getId() == null) {
            id = userBalanceRepository.getUserBalanceSequence();
            userBalance.setId(id);
        } else {
            id = userBalance.getId();
        }
        List<UserBalanceHistory> history = userBalanceHistoryRepository.findByIdOrderByUserBalanceIdDesc(userBalance.getId());
        for (int x = 0; x < userBalance.getUserBalanceHistories().size(); x++) {
            Integer detailId = x + 1;
            if (!history.isEmpty()) {
                detailId = history.get(0).getUserBalanceId() + detailId;
            }
            userBalance.getUserBalanceHistories().get(x).setId(id);
            userBalance.getUserBalanceHistories().get(x).setUserBalanceId(detailId);
        }
        userBalanceRepository.save(userBalance);
    }
}
