package br.com.alura.aluraflix.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "videos")
@Getter
@Setter
public class Video {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String url;

    public Video() {
    }

    public Video(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

}
