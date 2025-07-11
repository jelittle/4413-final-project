package com.user.userService.models;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class ECommerceUser implements UserDetails,CredentialsContainer{

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String Email;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;
    
    private boolean enabled = true;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private Set<String> authorities;
    //empty constructer
    public ECommerceUser() {}

    //getters and setters:
    public void setAuthorities(Set<String> authorities) { this.authorities = authorities;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

   

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password;}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username=username; }

    public void setEnabled(boolean val){this.enabled = val;}
    public boolean getEnabled(){return enabled;}

    public String getEmail() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public String getFirstName() { return first_name; }
    public void setFirstName(String first_name) { this.first_name = first_name; }

    public String getLastName() { return last_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }

    public long getId() { return id; }

    





    @Override
    public void eraseCredentials() {
        this.password = null;
    }





}

