package tn.microfinance.fincro.dao.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Address adress;
    private String profession;
    private Date lastLoginDate;
    private String gender;
    @Temporal(TemporalType.DATE)
    private Date lastLoginDateDisplay;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private Date joinDate;
    private String username;
    private String password;
    private String email;
    private float surplusRatio;
    private String profileImageUrl;
    private float salary;
    private int cin;
//
    @Enumerated(EnumType.STRING)
    private PersonalSituation personalSituation;

    private Date birthDate;
    private int guarantorSalary;
    private int score;
    private String role;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;

    @OneToOne(mappedBy = "user")
    private Account account;
    @OneToMany(mappedBy = "fkClient")
    private List<InsurenceContract> insurenceContract;



}
