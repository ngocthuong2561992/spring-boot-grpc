package stb.com.vn.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stb.com.vn.entity.Balance;
import stb.com.vn.entity.BalanceId;

@Repository
public interface WalletServiceRepository extends CrudRepository<Balance, BalanceId> {
}
