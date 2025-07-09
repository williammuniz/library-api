package com.library.library.service;


import com.library.library.dto.CadastroLivroDTO;
import com.library.library.enums.GeneroLivro;
import com.library.library.model.Autor;
import com.library.library.model.Livro;
import com.library.library.repository.AutorRepository;
import com.library.library.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Optional<Livro> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Livro> findAll() {
        return repository.findAll();
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

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public Page<Livro> pesquisar(String isbn, String titulo, GeneroLivro genero, Integer page, Integer pageSize) {
        var livro = Livro.builder().isbn(isbn).titulo(titulo).genero(genero).build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase("id")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Livro> livroExample = Example.of(livro, matcher);
        Pageable pageable = PageRequest.of(page, pageSize);
        return repository.findAll(livroExample, pageable);
    }
}
