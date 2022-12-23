package stb.com.vn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stb.com.vn.entity.Balance;
import stb.com.vn.entity.BalanceId;
import stb.com.vn.repositories.BalanceRepository;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Service
public class WalletService {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("EUR", "USD", "GBP");


    @Autowired
    private final BalanceRepository repository;

    public WalletService(BalanceRepository repository) {
        this.repository = repository;
    }


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
        } else {
            throw new WalletException("insufficient funds");
        }
    }

    private void validateCurrency(String currency) throws WalletException {
        if (!SUPPORTED_CURRENCIES.contains(currency)) {
            throw new WalletException("unknown currency");
        }
    }
}
