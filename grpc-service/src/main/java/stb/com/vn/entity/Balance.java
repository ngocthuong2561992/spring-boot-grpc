package stb.com.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Data
@Builder
@AllArgsConstructor
@Table(name = "`Balance`")
@Entity
public class Balance implements Serializable {
    @Id
    private BalanceId id;
    // use int for simplicity
    private int amount;

    public Balance() { }

    public Balance(String userId, String currency, int amount) {
        this.amount = amount;
        this.id = new BalanceId(userId, currency);
    }

    public BalanceId getId() {
        return id;
    }

    public void setId(BalanceId id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return amount == balance.amount &&
                Objects.equals(id, balance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}


