package br.com.alura.aluraflix.controller;

import br.com.alura.aluraflix.controller.dto.VideoDTO;
import br.com.alura.aluraflix.model.Video;
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

    @GetMapping
    public List<VideoDTO> listar() {
        return VideoDTO.toListDTO(videoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<VideoDTO> cadastrar(@RequestBody @Valid VideoDTO form, UriComponentsBuilder uriBuilder) {
        Video video = form.toEntity();
        videoRepository.save(video);

        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoDTO(video));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> detalhar(@PathVariable String id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            return new ResponseEntity<>(new VideoDTO(video.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> alterar(@PathVariable String id, @RequestBody @Valid VideoDTO form) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            Video entity = video.get();
            entity.setDescricao(form.getDescricao());
            entity.setTitulo(form.getTitulo());
            entity.setUrl(form.getUrl());
            videoRepository.save(entity);
            return new ResponseEntity<>(new VideoDTO(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            videoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
