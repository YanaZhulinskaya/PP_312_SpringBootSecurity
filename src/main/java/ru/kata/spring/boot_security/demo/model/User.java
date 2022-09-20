package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "MyFirstWebApp")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String userName;

    @Column(name = "Lastname")
    private String lastName;

    @Column(name = "Age")
    private Byte age;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<ru.kata.spring.boot_security.demo.model.Role> roles = new HashSet();

    public User() {
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.getRoles();
    }

    public boolean isAccountNonExpired() {

        return true;
    }

    public boolean isAccountNonLocked() {

        return true;
    }

    public boolean isCredentialsNonExpired() {

        return true;
    }

    public boolean isEnabled() {

        return true;
    }

    public Long getId() {

        return this.id;
    }

    public String getUsername() {

        return this.userName;
    }


    public String getLastname() {

        return this.lastName;
    }

    public Byte getAge() {

        return this.age;
    }

    public String getPassword() {

        return this.password;
    }

    public Collection<ru.kata.spring.boot_security.demo.model.Role> getRoles() {

        return this.roles;
    }

    public void setId(final Long id) {

        this.id = id;
    }

    public void setUsername(final String username) {

        this.userName = username;
    }

    public void setAge(final Byte age) {

        this.age = age;
    }

    public void setPassword(final String password) {

        this.password = password;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }
}
