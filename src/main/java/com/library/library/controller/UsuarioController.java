package com.library.library.controller;


import com.library.library.dto.UsuarioDTO;
import com.library.library.model.Usuario;
import com.library.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = dto.toEntity();
        service.salvar(usuario);
    }
}
