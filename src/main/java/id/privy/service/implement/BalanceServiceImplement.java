package id.privy.service.implement;

import id.privy.entity.BankBalance;
import id.privy.entity.BankBalanceHistory;
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
        if (userBalance.getId() == null) {
            Integer id = 0;
            if (userBalance.getId() == null) {
                id = userBalanceRepository.getUserBalanceSequence();
                userBalance.setId(id);
            } else {
                id = userBalance.getId();
            }
            List<UserBalanceHistory> history = userBalanceHistoryRepository.findByIdOrderByUserBalanceIdDesc(id);
            for (int x = 0; x < userBalance.getUserBalanceHistories().size(); x++) {
                Integer detailId = x + 1;
                if (!history.isEmpty()) {
                    detailId = history.get(0).getUserBalanceId() + detailId;
                }
                userBalance.getUserBalanceHistories().get(x).setId(id);
                userBalance.getUserBalanceHistories().get(x).setUserBalanceId(detailId);
                userBalance.getUserBalanceHistories().get(x).setIdHistory("User" + id + detailId);
            }
        }
        userBalanceRepository.save(userBalance);
    }

    @Override
    public void saveBankBalance(BankBalance bankBalance) {
        Integer id = 0;
        if (bankBalance.getId() != null) {
            id = bankBalance.getId();
        } else {
            id = bankBalanceRepository.getBankBalanceSequence();
            bankBalance.setId(id);
        }
        List<BankBalanceHistory> banks = bankBalanceHistoryRepository.findByIdOrderByBankBalanceIdDesc(id);
        for (int c = 0; c < bankBalance.getBankBalanceHistories().size(); c++) {
            Integer historyId = c + 1;
            if (!banks.isEmpty()) {
                historyId = banks.get(0).getBankBalanceId() + historyId;
            }
            bankBalance.getBankBalanceHistories().get(c).setIdHistory("Bank" + id + historyId);
            bankBalance.getBankBalanceHistories().get(c).setBankBalanceId(historyId);
            bankBalance.getBankBalanceHistories().get(c).setId(id);
        }
        bankBalanceRepository.save(bankBalance);
    }
}
