package tn.microfinance.fincro.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long idAccount;
    private double balance;
    private String password;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    @Column(nullable = false)
    private Date accountCreationDate;
    @JsonIgnore
    @ManyToOne
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
    @JsonIgnore
    @OneToMany(mappedBy = "accountFK")
    private List<MicroCredit> microCredits;

    @JsonIgnore
    @OneToMany(mappedBy = "accountIv")
    private List<Investment> investments;

    private boolean secured;
    private String aleaCode;


}
