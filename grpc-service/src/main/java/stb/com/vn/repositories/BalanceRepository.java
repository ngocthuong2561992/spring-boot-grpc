package stb.com.vn.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stb.com.vn.entity.Balance;
import stb.com.vn.entity.BalanceId;

import java.util.List;

//import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;


@Repository
public interface BalanceRepository extends CrudRepository<Balance, BalanceId> {

    List<Balance> findAllById_UserId(String userId);

//    @Lock(PESSIMISTIC_WRITE)
    Balance findBalanceById(BalanceId id);
}
