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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String address;
    private String profileImageUrl;
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

    @OneToMany(mappedBy = "user")
    private List<Account>account;
    @OneToMany(mappedBy = "fkClient")
    private List<InsurenceContract> insurenceContract;



}
