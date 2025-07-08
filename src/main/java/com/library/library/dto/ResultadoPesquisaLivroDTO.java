package com.library.library.dto;

import com.library.library.enums.GeneroLivro;
import com.library.library.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor
) {

    public static ResultadoPesquisaLivroDTO toDTO(Livro livro) {
        return new ResultadoPesquisaLivroDTO(livro.getId(), livro.getIsbn(), livro.getTitulo(), livro.getDataPulicacao(),
                livro.getGenero(), livro.getPreco(), AutorDTO.toDTO(livro.getAutor()));
    }
}
