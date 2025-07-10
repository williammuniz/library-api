package com.library.library.dto;

import com.library.library.model.Usuario;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {

    public Usuario toEntity() {
        return Usuario
                .builder()
                .login(login)
                .senha(senha)
                .roles(roles)
                .build();
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getLogin(), usuario.getSenha(), usuario.getRoles());
    }
}
