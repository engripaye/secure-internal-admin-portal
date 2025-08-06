package org.engripaye.secureinternaladminportal.model;

import jakarta.persistence.*;

@Entity
@Table(name ="permissions")
public class Permission {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
