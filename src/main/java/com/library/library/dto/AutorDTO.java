package com.library.library.dto;

import com.library.library.model.Autor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       @NotBlank(message = "Campo Obrigatório")
                       @Size(max = 100, min = 2, message = "Campo fora do tamanho padrão")
                       String nome,
                       @NotNull(message = "Campo Obrigatório")
                       @Past(message = "Não pode ser uma data futura")
                       LocalDate dataNascimento,
                       @NotBlank(message = "Campo Obrigatório")
                       String nacionalidade) {

    public Autor toEntity() {
        return Autor
                .builder()
                .nome(nome)
                .dataNascimento(dataNascimento)
                .nacionalidade(nacionalidade)
                .build();
    }

    public static AutorDTO toDTO(Autor autor) {
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
    }
}
