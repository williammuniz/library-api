package com.library.library.service;

import com.library.library.exceptions.OperacaoNaoPermitidaException;
import com.library.library.model.Autor;
import com.library.library.repository.AutorRepository;
import com.library.library.repository.LivroRepository;
import com.library.library.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public void salvar(Autor autor) {
        validator.validar(autor);
        repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar precisar ter o autor salvo na base de dados");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Autor> findAll() {
        return repository.findAll();
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um Ator que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        return repository.findByNomeOrNacionalidade(nome, nacionalidade);
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = Autor.builder().nome(nome).nacionalidade(nacionalidade).build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);

    }
}
