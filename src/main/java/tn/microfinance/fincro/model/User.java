package tn.microfinance.fincro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
@Entity
@Table
public class User implements Serializable {
    @Id
    private Integer idUser;
    private Integer cin;
    private String lastName;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private Integer phoneNumber;
    private String email;
    private String password;
    private String adress;
    private String profession;
    private Float salary;
    @Temporal(TemporalType.DATE)
    private Date accountCreationDate;
    @Enumerated(EnumType.STRING)
    private Role Role;
}
