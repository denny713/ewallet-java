package id.privy.service.implement;

import id.privy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserBalanceRepository userBalanceRepository;

    @Autowired
    protected UserBalanceHistoryRepository userBalanceHistoryRepository;

    @Autowired
    protected BankBalanceRepository bankBalanceRepository;

    @Autowired
    protected BankBalanceHistoryRepository bankBalanceHistoryRepository;
}
