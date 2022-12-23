package stb.com.vn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stb.com.vn.entity.Balance;
import stb.com.vn.entity.BalanceId;
import stb.com.vn.repositories.BalanceRepository;
import stb.com.vn.services.WalletException;
import stb.com.vn.services.WalletService;

import javax.transaction.Transactional;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
public class WalletServiceImpl implements WalletService {
    //    private static final Set<String> SUPPORTED_CURRENCIES = Set.of(e1:"VND", "USD");
    enum SUPPORTED_CURRENCIES {
        VND, USD
    }

    @Autowired
    private BalanceRepository repository;

    public Map<String, Integer> getBalances(String userId) {
        return repository.findAllById_UserId(userId).stream()
                .collect(toMap(
                        balance -> balance.getId().getCurrency(),
                        Balance::getAmount
                ));
    }

    @Transactional
    public void deposit(String userId, String currency, int amount) throws WalletException {
        validateCurrency(currency);
        BalanceId id = new BalanceId(userId, currency);
        Balance balance = repository.findBalanceById(id);
        if (balance != null) {
            balance.setAmount(balance.getAmount() + amount);
        } else {
            repository.save(new Balance(userId, currency, amount));
        }
    }

    @Transactional
    public void withdraw(String userId, String currency, int amount) throws WalletException {
        validateCurrency(currency);
        Balance balance = repository.findBalanceById(new BalanceId(userId, currency));
        if (balance != null && balance.getAmount() >= amount) {
            balance.setAmount(balance.getAmount() - amount);
        }
    }

    private void validateCurrency(String currency) throws WalletException {
        if (!SUPPORTED_CURRENCIES.VND.equals("VND") && SUPPORTED_CURRENCIES.USD.equals("USD")) {
            throw new WalletException("unknown currency");
        }
    }

}
