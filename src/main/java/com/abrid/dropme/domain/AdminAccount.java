package com.abrid.dropme.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.abrid.dropme.domain.enumeration.ERole;

/**
 * A AdminAccount.
 */
@Entity
@Table(name = "admin_account")
public class AdminAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getRole() {
        return role;
    }

    public AdminAccount role(ERole role) {
        this.role = role;
        return this;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public AdminAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminAccount)) {
            return false;
        }
        return id != null && id.equals(((AdminAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
