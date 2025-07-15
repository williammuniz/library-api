package com.library.library.dto;

import com.library.library.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "campo obrigatório")
        String login,
        @Email(message = "inválido")
        @NotBlank(message = "campo obrigatório")
        String email,
        @NotBlank(message = "campo obrigatório")
        String senha,
        List<String> roles) {

    public Usuario toEntity() {
        return Usuario
                .builder()
                .login(login)
                .email(email)
                .senha(senha)
                .roles(roles)
                .build();
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getLogin(), usuario.getEmail(), usuario.getSenha(), usuario.getRoles());
    }
}
