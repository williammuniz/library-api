package com.library.library.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    @NotNull
    private String login;
    @Column(nullable = false)
    private String senha;

    @Type(ListArrayType.class)
    @Builder.Default
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles = List.of();

}
