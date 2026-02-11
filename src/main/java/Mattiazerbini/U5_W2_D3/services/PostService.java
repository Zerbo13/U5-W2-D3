package Mattiazerbini.U5_W2_D3.services;

import Mattiazerbini.U5_W2_D3.entities.Post;
import Mattiazerbini.U5_W2_D3.exception.NotFoundException;
import Mattiazerbini.U5_W2_D3.payloads.PostPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {
    private List<Post> postDB = new ArrayList<>();

    public List<Post> findAll(){
        return this.postDB;
    }

    public Post salvaPost(PostPayload payload){
        Post newPost = new Post(payload.getCategoria(), payload.getTitolo(),
                payload.getContenuto(), payload.getTempoDiLettura());
        this.postDB.add(newPost);
        log.info("Il post "+newPost.getTitolo()+ " è stato inserito!");
        return newPost;
    }

    public Post findById(long idPost){
        Post find = null;
        for (Post post : this.postDB) {
            if (post.getId() == idPost) find = post;
        }
        if (find == null) throw new NotFoundException(idPost);
        return find;
    }

    public Post findByIdAndUpdate(long idPost, PostPayload payload) {
        Post find = null;
        for (Post post : this.postDB) {
            if (post.getId() == idPost) {
                find = post;
                find.setCategoria(payload.getCategoria());
                find.setTitolo(payload.getTitolo());
                find.setContenuto(payload.getContenuto());
                find.setTempoDiLettura(payload.getTempoDiLettura());
            }
        }
        if (find == null) throw new NotFoundException(idPost);
        return find;
    }

    public void findByIdAndDelete(long idPost) {
        Post find = null;
        for (Post post : this.postDB) {
            if (post.getId() == idPost) find = post;
        }
        if (find == null) throw new NotFoundException(idPost);
        this.postDB.remove(find);
    }
}
