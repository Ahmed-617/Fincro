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
@NoArgsConstructor

public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClaim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimType claimType;

    @Column(nullable = false)
    private String ClaimDescription;

    @Temporal(TemporalType.DATE)
    private Date ClaimDate;




}
