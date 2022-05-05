package tn.microfinance.fincro.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Hashtable;

@Entity
@Table( name = "MICROCREDIT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MicroCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredit;

    @Temporal(TemporalType.DATE)
   // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
    @NotNull(message = "Start Date cannot be empty")
    private Date startDate;

    @Min(value = 2, message = "Period must be equal or greater than 2")
    @Max(value = 84, message = "Period must be equal or less than 48")
    @NotNull(message = "Period cannot be empty")
    private Integer period;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Credit Status cannot be empty")
    private CreditStatus status;

    @Min(value = 100, message = "Amount must be equal or greater than 100 DTN")
    @Max(value = 40000, message = "Amount must be equal or less than than  40.000 DTN")
    @NotNull(message = "Amount cannot be empty")
    private Double amountCredit;

    @NotNull(message = "Remaining Amount cannot be empty")
    private Double amountRemaining;

    @NotNull(message = "Payed Amount cannot be empty")
    private Double payedAmount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Credit Type cannot be empty")
    private CreditType creditType;

    @NotNull(message = "Interest Rate cannot be empty")
    @Min(value = 7,message = "Interest Rate can't be lower than 7")
    private Double interestRate;

    private String cinGuarantor;

    private String guarantorFile;

    private String typePeriod;

    @Transient
    private static double TMM=6.25;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private  Account accountFK;

    public static double getTMM() {
        return TMM;
    }
}
