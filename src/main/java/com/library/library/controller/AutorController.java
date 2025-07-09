package com.library.library.controller;

import com.library.library.dto.AutorDTO;
import com.library.library.dto.ErroResposta;
import com.library.library.exceptions.OperacaoNaoPermitidaException;
import com.library.library.exceptions.RegistroDuplicadoException;
import com.library.library.model.Autor;
import com.library.library.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = dto.toEntity();
        service.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> findById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Autor> optional = service.findById(uuid);
        if (optional.isPresent()) {
            Autor autor = optional.get();
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok().body(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Autor> optional = service.findById(uuid);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(optional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> autores = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> dtos = autores.stream().map(autor -> new AutorDTO(
                autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        UUID uuid = UUID.fromString(id);
        Optional<Autor> autor = service.findById(uuid);

        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Autor entity = autor.get();
        entity.setNome(dto.nome());
        entity.setNacionalidade(dto.nacionalidade());
        entity.setDataNascimento(dto.dataNascimento());

        service.atualizar(entity);

        return ResponseEntity.noContent().build();
    }
}
