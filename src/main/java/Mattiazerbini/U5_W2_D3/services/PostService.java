package Mattiazerbini.U5_W2_D3.services;

import Mattiazerbini.U5_W2_D3.entities.Autore;
import Mattiazerbini.U5_W2_D3.entities.Post;
import Mattiazerbini.U5_W2_D3.exception.NotFoundException;
import Mattiazerbini.U5_W2_D3.payloads.PostPayload;
import Mattiazerbini.U5_W2_D3.repositories.AutoreRepository;
import Mattiazerbini.U5_W2_D3.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post salvaPost(PostPayload payload) {
        Post newPost = new Post(payload.getCategoria(), payload.getTitolo(),
                payload.getContenuto(), payload.getTempoDiLettura());
        Post postSalvato = this.postRepository.save(newPost);
        log.info("Il post " + newPost.getTitolo() + " è stato inserito!");
        return newPost;
    }

    public Post findById(long idPost) {
        return this.postRepository.findById(idPost)
                .orElseThrow(() -> new NotFoundException(idPost));
    }

    public Post findByIdAndUpdate(long idPost, PostPayload payload) {
        Post find = this.findById(idPost);
        Post post = null;
        if (post.getId() == idPost) {
            find = post;
            find.setCategoria(payload.getCategoria());
            find.setTitolo(payload.getTitolo());
            find.setContenuto(payload.getContenuto());
            find.setTempoDiLettura(payload.getTempoDiLettura());
            find.setCover("https://picsum.photos/200/300");
        }
        Post postModificato = this.postRepository.save(find);
        log.info("Il post " + postModificato.getTitolo() + " è stato modificato");
        return postModificato;
    }

    public void findByIdAndDelete(long idPost) {
        Post find = this.findById(idPost);
        this.postRepository.delete(find);
    }
}