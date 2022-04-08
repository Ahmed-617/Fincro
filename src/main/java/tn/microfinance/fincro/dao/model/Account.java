package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccount;
    private float balance;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date accountCreationDate;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "accountFK")
    private List<MicroCredit> microCredits;

    public Integer getIdBalance() {
        return idAccount;
    }

    public float getAmount() {
        return balance;
    }

    public void setAmount(float amount) {
        this.balance = amount;
    }
}
