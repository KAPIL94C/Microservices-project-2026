package com.accenture.userservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(unique = true, nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }

}
