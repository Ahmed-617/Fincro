package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table (name = "CASEINSURANCE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CaseInsurance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idCase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseInsuranceStatus status;

    @Column(nullable = false)
    private Integer Benefits;


    @Column(nullable = false)
    private Integer BenefitRemaining;

    @OneToOne(mappedBy = "caseInsur")
    private Claim claim;

    @OneToOne(mappedBy = "caseInsurance")
    private InsurenceContract contract;





}
