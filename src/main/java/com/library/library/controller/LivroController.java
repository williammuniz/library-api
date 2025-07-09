package com.library.library.controller;

import com.library.library.dto.CadastroLivroDTO;
import com.library.library.dto.ResultadoPesquisaLivroDTO;
import com.library.library.model.Livro;
import com.library.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = service.salvar(dto);

        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> findById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return service.findById(uuid).map(livro -> {
            var dto = ResultadoPesquisaLivroDTO.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }
}
