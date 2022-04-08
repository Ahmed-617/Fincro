package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="INSUREDPROPERTY")
@Table(name ="INSUREDPROPERTY")
public class InsuredProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProperty; // Clé primaire


    private Double propertyValue;


    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;


    private boolean visibility;

    private String TitreDePropriete;

    @Override
    public String toString() {
        return "InsuredProperty [idProperty=" + idProperty + ", propertyValue=" + propertyValue + ", propertyType="
                + propertyType + ", visibility=" + visibility + "]";
    }
}