package com.library.library.controller;

import com.library.library.dto.AutorDTO;
import com.library.library.model.Autor;
import com.library.library.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
@Tag(name="Autores")
public class AutorController implements GenericController {

    private final AutorService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Salvar", description = "Cadastrar novo Autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = dto.toEntity();
        service.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obter detalhes", description = "Retorna os dados do autor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
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
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Autor possuí livro cadastrado.."),
    })
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),
    })
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> autores = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> dtos = autores.stream().map(autor -> new AutorDTO(
                autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar", description = "Atualiza de autores por parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
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
