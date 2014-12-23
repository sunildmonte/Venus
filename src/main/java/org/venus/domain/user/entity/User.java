package org.venus.domain.user.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "rdc_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING, length = 30)
public class User {

    public static enum UserType {RDMAIN, RDLIFE, RDMARKET, ADMIN};
    public static enum ACTIVE {Y, N};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rdc_user")
    @SequenceGenerator(name = "seq_rdc_user", sequenceName = "seq_rdc_user", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    private String Name;

    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(name = "pwd", nullable = false)
    @NotNull
    private String password;

    private Address address;

    @Column(name = "emailId")
    private String emailId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "pwd_expiry_date")
    private Date pwdExpiryDate;

    @Version
    @Column(name = "version")
    private Long version;

    @Override
    public String toString() {
        return new StringBuilder()
        .append(id)
        .append("-")
        .append(username)
        .toString();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

     public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public Date getPwdExpiryDate() {
        return pwdExpiryDate;
    }

    public void setPwdExpiryDate(Date pwdExpiryDate) {
        this.pwdExpiryDate = pwdExpiryDate;
    }

    public Long getVersion() {
        return version;
    }
}
