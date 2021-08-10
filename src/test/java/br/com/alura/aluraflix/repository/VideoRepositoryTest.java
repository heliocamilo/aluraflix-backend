package br.com.alura.aluraflix.repository;

import br.com.alura.aluraflix.model.Categoria;
import br.com.alura.aluraflix.model.Video;
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
public class VideoRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TestEntityManager em;

    private static final String TITULO_VIDEO = "Titulo video teste";

    @Test
    public void deveCadastrarVideoComCategoria() {

        Categoria categoria = this.salvarCategoria();
        Video video = this.salvarVideo(categoria);

        Assert.assertNotNull(video);
        Assert.assertTrue(video.getId() > 0);
        Long idVideo = video.getId();

        Video videoConsultado = videoRepository.getOne(idVideo);
        Assert.assertNotNull(videoConsultado.getCategoria());
        Assert.assertTrue(videoConsultado.getCategoria().getId().longValue() == categoria.getId().longValue());

    }


    @Test
    public void deveCadastrarVideoSemCategoria() {

        Categoria categoria = null;
        Video video = this.salvarVideo(categoria);

        Assert.assertNotNull(video);
        Assert.assertTrue(video.getId() > 0);
        Long idVideo = video.getId();

        Video videoConsultado = videoRepository.getOne(idVideo);
        Assert.assertNotNull(videoConsultado);
        Assert.assertNull(videoConsultado.getCategoria());

    }

    @Test
    public void deveAlterarVideo() {
        Categoria categoria = null;
        Video video = this.salvarVideo(categoria);
        Long ID_VIDEO = video.getId();

        Assert.assertNotNull(video);
        Assert.assertTrue(ID_VIDEO > 0);

        Categoria novaCategoria = this.salvarCategoria();
        Video videoProcurado = videoRepository.getOne(ID_VIDEO);
        Assert.assertNull(videoProcurado.getCategoria());

        videoProcurado.setCategoria(novaCategoria);
        videoRepository.save(videoProcurado);

        Video videoAlterado = videoRepository.getOne(ID_VIDEO);
        Assert.assertNotNull(videoAlterado);
        Assert.assertNotNull(videoAlterado.getCategoria());
        Assert.assertTrue(videoAlterado.getCategoria().getId().longValue() == novaCategoria.getId().longValue());

    }

    @Test
    public void deveConsultarVideoPorCategoria() {
        Categoria categoria = this.salvarCategoria();
        Video video = this.salvarVideo(categoria);

        List<Video> videos = videoRepository.findByCategoriaId(categoria.getId());
        Assert.assertNotNull(videos);
        Assert.assertTrue(videos.get(0).getCategoria().getId().longValue() == categoria.getId());
    }

    @Test
    public void deveConsultaVideoPorTitulo() {

        Categoria categoria = null;
        Video video = this.salvarVideo(categoria);

        List<Video> videos = videoRepository.findByTitulo(TITULO_VIDEO);
        Assert.assertNotNull(videos);
        Assert.assertTrue(videos.size() > 0);
        Assert.assertTrue(videos.get(0).getTitulo().equals(TITULO_VIDEO));
    }

    @Test
    public void deveListarVideo() {
        Categoria categoria = null;
        Video video = this.salvarVideo(categoria);

        List<Video> videos = videoRepository.findAll();
        Assert.assertNotNull(videos);
        Assert.assertTrue(videos.size() > 0);
    }

    @Test
    public void deveExcluirVideo() {
        Categoria categoria = null;
        Video video = this.salvarVideo(null);
        Long ID_VIDEO = video.getId();

        Optional<Video> videoSalvo = videoRepository.findById(ID_VIDEO);
        Assert.assertTrue(videoSalvo.isPresent());
        Assert.assertNotNull(videoSalvo.get());
        Assert.assertTrue(videoSalvo.get().getId().equals(ID_VIDEO));

        videoRepository.delete(video);

        Optional<Video> videoProcurado = videoRepository.findById(ID_VIDEO);
        Assert.assertFalse(videoProcurado.isPresent());
    }


    private Categoria salvarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setTitulo("Categoria teste");
        categoria.setCor("#FFFFFF");

        Categoria newCategoria = categoriaRepository.save(categoria);

        return newCategoria;
    }


    private Video salvarVideo(Categoria categoria) {
        Video video = new Video();
        video.setTitulo(TITULO_VIDEO);
        video.setUrl("http://video.teste.br");
        video.setDescricao("Descricao video teste");
        video.setCategoria(categoria);

        Video newVideo = videoRepository.save(video);

        return newVideo;
    }

}
