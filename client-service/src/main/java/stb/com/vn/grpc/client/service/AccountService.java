package stb.com.vn.grpc.client.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import stb.com.vn.*;

@Service
public class AccountService {

    @GrpcClient("grpc-stb-service")
    WalletGrpc.WalletBlockingStub synchronousClient;

    @GrpcClient("grpc-stb-service")
    WalletGrpc.WalletStub asynchronousClient;

    public void deposit(String userId, int amount, String currency) {
        ChangeBalanceRequest authorRequest = ChangeBalanceRequest.newBuilder().setUserId(userId).setAmount(amount).setCurrency(currency).build();
        Empty authorResponse = synchronousClient.deposit(authorRequest);
    }

    public void withdraw(String userId, int amount, String currency) {
        ChangeBalanceRequest authorRequest = ChangeBalanceRequest.newBuilder().setUserId(userId).setAmount(amount).setCurrency(currency).build();
        Empty authorResponse = synchronousClient.deposit(authorRequest);
    }





}
