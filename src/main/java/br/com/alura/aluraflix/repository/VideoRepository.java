package br.com.alura.aluraflix.repository;

import br.com.alura.aluraflix.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByCategoriaId(Long id);
    List<Video> findByTitulo(String titulo);
}
