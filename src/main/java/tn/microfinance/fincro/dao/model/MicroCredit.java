package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "MICROCREDIT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MicroCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredit;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditStatus status;

    @Column(nullable = false)
    private Double ammountCredit;

    @Column(nullable = false)
    private Double ammountRemaining;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditType creditType;

    @Column(nullable = false)
    private Double interestRate;

    private String cinGuarantor;

    private String guarantorFile;
}