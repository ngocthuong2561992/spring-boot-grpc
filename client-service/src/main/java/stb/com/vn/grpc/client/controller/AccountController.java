package stb.com.vn.grpc.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stb.com.vn.grpc.client.dto.Balance;
import stb.com.vn.grpc.client.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountController {

    @Autowired
    public AccountService accountService;

    @PostMapping ("/deposit")
    public ResponseEntity<?>  deposit(@RequestBody Balance balance){
        try {
            accountService.deposit(balance.getUserId(), Integer.valueOf(balance.getAmount()),balance.getCurrency());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping ("/withdraw")
    public ResponseEntity<?>  withdraw(@RequestBody Balance balance){
        try {
            accountService.deposit(balance.getUserId(), Integer.valueOf(balance.getAmount()),balance.getCurrency());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
