package Mattiazerbini.U5_W2_D3.services;

import Mattiazerbini.U5_W2_D3.entities.Autore;
import Mattiazerbini.U5_W2_D3.entities.Post;
import Mattiazerbini.U5_W2_D3.exception.NotFoundException;
import Mattiazerbini.U5_W2_D3.exception.ValidationException;
import Mattiazerbini.U5_W2_D3.payloads.PostPayload;
import Mattiazerbini.U5_W2_D3.repositories.AutoreRepository;
import Mattiazerbini.U5_W2_D3.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final AutoreRepository autoreRepository;

    @Autowired
    public PostService(PostRepository postRepository, AutoreRepository autoreRepository) {
        this.postRepository = postRepository;
        this.autoreRepository = autoreRepository;
    }

    public Post salvaPost(PostPayload payload) {
        if(payload.getIdAutore() == null){
            throw new ValidationException("ID autore non può essere null");
        }
        Autore autore = this.autoreRepository.findById(payload.getIdAutore())
                .orElseThrow(() -> new ValidationException("Autore non trovato"));
        Post newPost = new Post(payload.getCategoria(), payload.getTitolo(),
                payload.getContenuto(), payload.getTempoDiLettura());
        newPost.setAutore(autore);
        Post postSalvato = this.postRepository.save(newPost);
        log.info("Il post " + newPost.getTitolo() + " è stato inserito!");
        return postSalvato;
    }

    public Page<Post> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.postRepository.findAll(pageable);
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