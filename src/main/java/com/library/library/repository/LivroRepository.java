package com.library.library.repository;

import com.library.library.model.Autor;
import com.library.library.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor ator);

    boolean existsByAutor(Autor autor);
}
