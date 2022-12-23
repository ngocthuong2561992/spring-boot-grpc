package stb.com.vn.services;


import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface WalletService {
    public void deposit(String userId, String currency, int amount) throws WalletException;

    public void withdraw(String userId, String currency, int amount) throws WalletException;


    public Map<String, Integer> getBalances(String userId) ;

}
