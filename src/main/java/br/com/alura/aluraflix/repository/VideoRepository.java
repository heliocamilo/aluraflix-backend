package br.com.alura.aluraflix.repository;

import br.com.alura.aluraflix.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
