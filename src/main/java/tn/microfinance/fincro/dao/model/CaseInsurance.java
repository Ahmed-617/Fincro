package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table (name = "CASEINSURANCE")
@Getter
@Setter
@AllArgsConstructor

public class CaseInsurance {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="idCase")
    private Integer idCase; // Clé primaire

    @Column(name="status")
    private Integer status;

    @Column(name="benefits")
    private Double benefits;

    @Column(name="remainingBenefits")
    private Double remainingBenefits;

    @Column(name="benefitsType")
    private Integer benefitsType;


    @ManyToOne(cascade = CascadeType.ALL)
    private InsurenceContract fkContract;

    @OneToOne
    private Claim fkClaim;

    @ManyToOne(cascade = CascadeType.ALL)
    private User fkEmployee;



    public CaseInsurance() {
        super();
    }

    public CaseInsurance(Integer status, Double benefits, Integer benefitsType) {
        super();
        this.status = status;
        this.benefits = benefits;
        this.benefitsType = benefitsType;
    }

    public Double getBenefits() {
        return benefits;
    }

    public void setBenefits(Double benefits) {
        this.benefits = benefits;
    }

    public Integer getBenefitsType() {
        return benefitsType;
    }

    public void setBenefitsType(Integer benefitsType) {
        this.benefitsType = benefitsType;
    }

    public Integer getIdCase() {
        return idCase;
    }

    public void setIdCase(Integer idCase) {
        this.idCase = idCase;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public InsurenceContract getFkContract() {
        return fkContract;
    }

    public void setFkContract(InsurenceContract fkContract) {
        this.fkContract = fkContract;
    }

    public Claim getFkClaim() {
        return fkClaim;
    }

    public void setFkClaim(Claim fkClaim) {
        this.fkClaim = fkClaim;
    }

    public User getFkEmployee() {
        return fkEmployee;
    }

    public void setFkEmployee(User fkEmployee) {
        this.fkEmployee = fkEmployee;
    }

    public double getRemainingBenefits() {
        return remainingBenefits;
    }

    public void setRemainingBenefits(Double remainingBenefits) {
        this.remainingBenefits = remainingBenefits;
    }



}
