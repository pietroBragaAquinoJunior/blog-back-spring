package com.pietro.blog_back_spring.entities;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, length = 100)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) 
    @JoinTable(
        name = "user_role", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions; // Ex: "CAN_READ_REPORTS"

    /**
     * O MÉTODO MAIS IMPORTANTE PARA AUTORIZAÇÃO.
     * O Spring chama isso para saber o que o usuário PODE FAZER.
     * * GrantedAuthority é a "moeda" da permissão no Spring.
     * - "ROLE_ADMIN": Convenção para papéis (usado por hasRole).
     * - "CAN_READ_REPORTS": Permissão de ação fina (usado por hasAuthority).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 1. Mapeia as Roles (Papéis)
        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
        
        // 2. Mapeia as Permissões Finas (Ações)
        if (permissions != null) {
            permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .forEach(authorities::add);
        }
        
        return authorities;
    }

    // (O restante dos métodos do UserDetails omitidos por brevidade)
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}