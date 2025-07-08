package com.library.library.service;


import com.library.library.dto.CadastroLivroDTO;
import com.library.library.model.Autor;
import com.library.library.model.Livro;
import com.library.library.repository.AutorRepository;
import com.library.library.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final AutorRepository autorRepository;

    public Livro salvar(CadastroLivroDTO dto) {
        Autor autor = autorRepository.findById(dto.idAutor())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com ID: " + dto.idAutor()));

        Livro livro = toEntity(dto, autor);

        return repository.save(livro);
    }

    public Optional<Livro> findById(UUID id){
        return repository.findById(id);
    }

    private static Livro toEntity(CadastroLivroDTO dto, Autor autor) {
        return Livro.builder()
                .isbn(dto.isbn())
                .titulo(dto.titulo())
                .dataPulicacao(dto.dataPublicacao())
                .genero(dto.genero())
                .preco(dto.preco())
                .autor(autor)
                .build();
    }
}
