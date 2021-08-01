package br.com.alura.aluraflix.controller;

import br.com.alura.aluraflix.controller.dto.VideoRequestDTO;
import br.com.alura.aluraflix.controller.dto.VideoResponseDTO;
import br.com.alura.aluraflix.model.Video;
import br.com.alura.aluraflix.repository.CategoriaRepository;
import br.com.alura.aluraflix.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping
    public List<VideoResponseDTO> listar() {
        return VideoResponseDTO.toListDTO(videoRepository.findAll());
    }

    @GetMapping(params = "search")
    public List<VideoResponseDTO> listarPorNome(@RequestParam(required = true) String search) {
        return VideoResponseDTO.toListDTO(videoRepository.findByTitulo(search));
    }

    @PostMapping
    public ResponseEntity<VideoRequestDTO> cadastrar(@RequestBody @Valid VideoRequestDTO form, UriComponentsBuilder uriBuilder) {
        Video video = form.toEntity(categoriaRepository);
        videoRepository.save(video);

        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoRequestDTO(video));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> detalhar(@PathVariable Long id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            return new ResponseEntity<>(new VideoResponseDTO(video.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<VideoRequestDTO> alterar(@PathVariable Long id, @RequestBody @Valid VideoRequestDTO form) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            Video entity = video.get();
            entity.setDescricao(form.getDescricao());
            entity.setTitulo(form.getTitulo());
            entity.setUrl(form.getUrl());
            videoRepository.save(entity);
            return new ResponseEntity<>(new VideoRequestDTO(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            videoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
