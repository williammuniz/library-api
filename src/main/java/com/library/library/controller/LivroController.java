package com.library.library.controller;

import com.library.library.dto.CadastroLivroDTO;
import com.library.library.dto.ResultadoPesquisaLivroDTO;
import com.library.library.enums.GeneroLivro;
import com.library.library.model.Livro;
import com.library.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class LivroController implements GenericController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = service.salvar(dto);

        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> findAll() {
        List<ResultadoPesquisaLivroDTO> resultado = service.findAll()
                .stream()
                .map(ResultadoPesquisaLivroDTO::toDTO)
                .toList();

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> findById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return service.findById(uuid).map(livro -> {
            var dto = ResultadoPesquisaLivroDTO.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        return service.findById(UUID.fromString(id)).map(livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @RequestMapping("/pesquisa")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisar(@RequestParam(value = "isbn", required = false) String isbn,
                                                                     @RequestParam(value = "titulo", required = false) String titulo,
                                                                     @RequestParam(value = "genero", required = false) String genero,
                                                                     @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        Page<Livro> livros = service.pesquisar(isbn, titulo, genero != null ? GeneroLivro.valueOf(genero) : null, page, pageSize);
        Page<ResultadoPesquisaLivroDTO> dtos = livros.map(ResultadoPesquisaLivroDTO::toDTO);
        return ResponseEntity.ok(dtos);
    }
}
