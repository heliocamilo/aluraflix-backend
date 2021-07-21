package br.com.alura.aluraflix.controller.dto;

import br.com.alura.aluraflix.model.Video;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VideoDTO {

    @NotNull @NotEmpty
    private String titulo;

    @NotNull @NotEmpty
    private String descricao;

    @NotNull @NotEmpty
    private String url;


    public VideoDTO(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

    public VideoDTO(Video video) {
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.url = video.getUrl();
    }

    public Video toEntity() {
        Video video = new Video(this.titulo, this.descricao, this.url);
        return video;
    }

    public static List<VideoDTO> toListDTO(List<Video> videos) {
        return videos.stream().map(VideoDTO::new).collect(Collectors.toList());
    }

}
