package br.com.alura.aluraflix.controller.dto;

import br.com.alura.aluraflix.model.Categoria;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoriaResponseDTO {

    private Long id;
    private String titulo;
    private String cor;

    public CategoriaResponseDTO(Long id, String titulo, String cor) {
        this.id = id;
        this.titulo = titulo;
        this.cor = cor;
    }

    public CategoriaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.titulo = categoria.getTitulo();
        this.cor = categoria.getCor();
    }

    public static List<CategoriaResponseDTO> toListDTO(List<Categoria> categorias) {
        return categorias.stream().map(CategoriaResponseDTO::new).collect(Collectors.toList());
    }

}
