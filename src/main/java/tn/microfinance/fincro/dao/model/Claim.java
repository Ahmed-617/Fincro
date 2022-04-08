package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Table (name = "CLAIM")
@Entity
@Getter
@Setter
@AllArgsConstructor


public class Claim {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="idClaim")
    private Integer idClaim; // Clé primaire


    @Column(name="claimType")
    @Enumerated(EnumType.STRING)
    private ClaimType claimType;

    @Column(name="descriptionClaim")
    private String descriptionClaim;

    @Column(name="claimDate")
    @Temporal (TemporalType.DATE)
    private Date claimDate;

    @Column(name="visibility")
    private boolean visibility;





    public Claim() {
        super();
    }

    public Claim(ClaimType claimType, String descriptionClaim, boolean visibility) {
        super();
        this.claimType = claimType;
        this.descriptionClaim = descriptionClaim;
        this.visibility = visibility;
    }

    public Integer getIdClaim() {
        return idClaim;
    }

    public void setIdClaim(Integer idClaim) {
        this.idClaim = idClaim;
    }

    public ClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public String getDescriptionClaim() {
        return descriptionClaim;
    }

    public void setDescriptionClaim(String descriptionClaim) {
        this.descriptionClaim = descriptionClaim;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }


}
