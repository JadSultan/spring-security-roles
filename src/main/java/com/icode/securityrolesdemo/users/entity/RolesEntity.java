package com.icode.securityrolesdemo.users.entity;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    public RolesEntity() {
    }

    public RolesEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
