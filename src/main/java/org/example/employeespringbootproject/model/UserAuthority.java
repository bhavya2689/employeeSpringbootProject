package org.example.employeespringbootproject.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users_authorities")
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuthority that = (UserAuthority) o;

        if (id != null && that.id != null) {
            // simple comparison by id
            return id.equals(that.id);
        } else {
            // complex comparison by user and authority
            return user.equals(that.user) && authority.equals(that.authority);
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 31;
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", authorityId=" + (authority != null ? authority.getId() : "null") +
                '}';
    }


}
