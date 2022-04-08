package tn.microfinance.fincro.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table( name = "INVESTMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvestment;
    @Enumerated(EnumType.STRING)
    private InvestmentType investmentType;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date startDate;
    private double amount;
    private double accValue;
    private double interest;
    private long proposerAccountId;
    private long investorAccountId;
    private long microCreditId;
    private String typePeriod;
    private int nbreOfPeriods;
    @OneToMany(mappedBy = "investment")
    private List<FileEntity> files;
    private String description;
    private String region;
    private String projectType;
    @ManyToOne
    private Account accountIv;
}
