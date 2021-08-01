package br.com.alura.aluraflix.controller.dto;

import br.com.alura.aluraflix.model.Categoria;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoriaRequestDTO {

    @NotNull @NotEmpty
    private String titulo;

    @NotNull @NotEmpty
    private String cor;

    public CategoriaRequestDTO(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
    }

    public CategoriaRequestDTO(Categoria categoria) {
        this.titulo = categoria.getTitulo();
        this.cor = categoria.getCor();
    }

    public Categoria toEntity() {
        Categoria categoria = new Categoria(this.titulo, this.cor);
        return categoria;
    }

    public static List<CategoriaRequestDTO> toListDTO(List<Categoria> categorias) {
        return categorias.stream().map(CategoriaRequestDTO::new).collect(Collectors.toList());
    }

}
