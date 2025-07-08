package com.library.library.controller;

import com.library.library.dto.AutorDTO;
import com.library.library.dto.CadastroLivroDTO;
import com.library.library.dto.ErroResposta;
import com.library.library.dto.ResultadoPesquisaLivroDTO;
import com.library.library.exceptions.RegistroDuplicadoException;
import com.library.library.model.Autor;
import com.library.library.model.Livro;
import com.library.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = service.salvar(dto);

            URI location = gerarHeaderLocation(livro.getId());

            return ResponseEntity.created(location).build();

        } catch (RegistroDuplicadoException ex) {
            var erroDto = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> findById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Livro> optional = service.findById(uuid);
        if (optional.isPresent()) {
            Livro livro = optional.get();
            ResultadoPesquisaLivroDTO dto = ResultadoPesquisaLivroDTO.toDTO(livro);
            return ResponseEntity.ok().body(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
