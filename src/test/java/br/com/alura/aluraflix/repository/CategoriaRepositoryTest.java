package br.com.alura.aluraflix.repository;

import br.com.alura.aluraflix.model.Categoria;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private TestEntityManager em;

    private static Long ID_CATEGORIA_LIVRE = 1L;
    private static String TITULO_CATEGORIA = "Categoria teste";

    @Test
    public void deveExistirCategoriaLivre() {

        Categoria categoria = repository.getOne(ID_CATEGORIA_LIVRE);
        Assert.assertNotNull(categoria);
        Assert.assertTrue(categoria.getTitulo().equals("Livre"));
        Assert.assertTrue(categoria.getCor().equals("#FF0000"));

    }

    @Test
    public void deveCadastrarCategoria() {

        Categoria categoria = this.salvarCategoria();

        Assert.assertNotNull(categoria);
        Assert.assertNotNull(categoria.getId());
        Assert.assertTrue(categoria.getId().longValue() > 1);

    }

    @Test
    public void deveAlterarCategoria() {

        String NOVO_TITULO = "Categoria teste 2";

        Categoria categoria = this.salvarCategoria();
        Assert.assertNotNull(categoria);
        Assert.assertTrue(categoria.getTitulo().equals(TITULO_CATEGORIA));
        Assert.assertFalse(categoria.getTitulo().equals(NOVO_TITULO));

        categoria.setTitulo(NOVO_TITULO);
        repository.save(categoria);

        Categoria categoriaAlterada = repository.getOne(categoria.getId());
        Assert.assertNotNull(categoriaAlterada);
        Assert.assertTrue(categoriaAlterada.getTitulo().equals(NOVO_TITULO));
    }

    @Test
    public void deveExcluirCategoria() {
        Categoria categoria = this.salvarCategoria();
        Assert.assertNotNull(categoria);
        Assert.assertTrue(categoria.getId().longValue() > 1);

        Long ID_CATEGORIA = categoria.getId();

        Categoria categoriaProcurada = repository.getOne(ID_CATEGORIA);
        Assert.assertNotNull(categoriaProcurada);

        repository.delete(categoria);

        Optional<Categoria> categoriaProcuradaNovamente = repository.findById(ID_CATEGORIA);
        Assert.assertFalse(categoriaProcuradaNovamente.isPresent());

    }

    @Test
    public void deveListarCategoria() {
        List<Categoria> lista = repository.findAll();
        Assert.assertNotNull(lista);
        Assert.assertTrue(lista.size() > 0);
    }


    private Categoria salvarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setTitulo(TITULO_CATEGORIA);
        categoria.setCor("#FFFFFF");

        Categoria newCategoria = repository.save(categoria);

        return newCategoria;
    }
}
