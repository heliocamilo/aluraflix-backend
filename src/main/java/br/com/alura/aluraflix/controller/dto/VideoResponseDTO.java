package br.com.alura.aluraflix.controller.dto;

import br.com.alura.aluraflix.model.Categoria;
import br.com.alura.aluraflix.model.Video;
import br.com.alura.aluraflix.repository.CategoriaRepository;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VideoResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private String categoria;


    public VideoResponseDTO(Long id, String titulo, String descricao, String url, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoria = categoria;
    }

    public VideoResponseDTO(Video video) {
        this.id = video.getId();
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
        this.categoria = video.getCategoria().getTitulo();
    }

    public static List<VideoResponseDTO> toListDTO(List<Video> videos) {
        return videos.stream().map(VideoResponseDTO::new).collect(Collectors.toList());
    }

}
