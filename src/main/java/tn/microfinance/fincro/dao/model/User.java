package tn.microfinance.fincro.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    private Integer cin;
    private String lastName;
    private String name;
    private String sexe;
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
    private Role Role;
    @OneToMany(mappedBy = "user")
    private List<Account>account;
    @OneToMany(mappedBy = "fkClient")
    private List<InsurenceContract> insurenceContract;


}
