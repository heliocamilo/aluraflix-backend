package br.com.alura.aluraflix.controller;

import br.com.alura.aluraflix.controller.dto.CategoriaRequestDTO;
import br.com.alura.aluraflix.controller.dto.CategoriaResponseDTO;
import br.com.alura.aluraflix.controller.dto.VideoResponseDTO;
import br.com.alura.aluraflix.model.Categoria;
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
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    VideoRepository videoRepository;

    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return CategoriaResponseDTO.toListDTO(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoriaRequestDTO> cadastrar(@RequestBody @Valid CategoriaRequestDTO form, UriComponentsBuilder uriBuilder) {
        Categoria categoria = form.toEntity();
        categoriaRepository.save(categoria);

        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaRequestDTO(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> detalhar(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return new ResponseEntity<>(new CategoriaResponseDTO(categoria.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/videos")
    public List<VideoResponseDTO> videosPorCategoria(@PathVariable Long id) {
        List<Video> videos = videoRepository.findByCategoriaId(id);
        return VideoResponseDTO.toListDTO(videos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRequestDTO> alterar(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO form) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            Categoria entity = categoria.get();
            entity.setTitulo(form.getTitulo());
            entity.setCor(form.getCor());
            categoriaRepository.save(entity);
            return new ResponseEntity<>(new CategoriaRequestDTO(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoriaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
