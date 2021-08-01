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
public class VideoRequestDTO {

    private static final Long CATEGORIA_LIVRE = 1L;

    @NotNull @NotEmpty
    private String titulo;

    @NotNull @NotEmpty
    private String descricao;

    @NotNull @NotEmpty
    private String url;

    private Long categoriaId;


    public VideoRequestDTO(String titulo, String descricao, String url, Long categoriaId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoriaId = categoriaId;
    }

    public VideoRequestDTO(Video video) {
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
        this.categoriaId = video.getCategoria().getId();
    }

    public Video toEntity(CategoriaRepository categoriaRepository) {
        if (categoriaId == null) {
            categoriaId = CATEGORIA_LIVRE;
        }
        Categoria categoria = categoriaRepository.getOne(categoriaId);
        Video video = new Video(this.titulo, this.descricao, this.url);
        video.setCategoria(categoria);
        return video;
    }

    public static List<VideoRequestDTO> toListDTO(List<Video> videos) {
        return videos.stream().map(VideoRequestDTO::new).collect(Collectors.toList());
    }

}
