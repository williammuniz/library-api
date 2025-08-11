package com.library.library.dto;

import com.library.library.model.Autor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(UUID id,
                       @NotBlank(message = "Campo Obrigatório")
                       @Size(max = 100, min = 2, message = "Campo fora do tamanho padrão")
                       @Schema(name = "nome")
                       String nome,
                       @NotNull(message = "Campo Obrigatório")
                       @Past(message = "Não pode ser uma data futura")
                       @Schema(name = "dataNascimento")
                       LocalDate dataNascimento,
                       @NotBlank(message = "Campo Obrigatório")
                       @Schema(name = "nacionalidade")
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
