package tn.microfinance.fincro.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name ="CONTRACT")
@Table(name ="CONTRACT")
public class InsurenceContract implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private int idContract; // Clé primaire


    @Temporal (TemporalType.DATE)
    private Date startDateContract;


    @Temporal (TemporalType.DATE)
    private Date dueDateContract;

    private String clauses;


    private Double premium;


    private int contractRank;


    private boolean visibility;

    @JsonIgnore
   @ManyToOne(cascade = CascadeType.ALL)
    private User fkClient;
    @JsonIgnore
    @OneToOne
    private InsuredProperty fkInsuredProperty;
    @JsonIgnore
   @OneToOne
    private CaseInsurance caseInsurance;


}
