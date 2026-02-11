package Mattiazerbini.U5_W2_D3.controllers;

import Mattiazerbini.U5_W2_D3.entities.Post;
import Mattiazerbini.U5_W2_D3.payloads.PostPayload;
import Mattiazerbini.U5_W2_D3.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    //LISTA DI POST (GET)
    @GetMapping
    public List<Post> findAll() {
        return this.postService.findAll();
    }

    //RITORNA UN SINGOLO POST (GET)
    @GetMapping("/{idPost}")
    public Post getPostById(@PathVariable long idPost) {
        return this.postService.findById(idPost);
    }


    //CREAZIONE DI UN NUOVO POST (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Post createPost(@RequestBody PostPayload payload) {
        return this.postService.salvaPost(payload);
    }


    //MODIFICA LO SPECIFICO POST (PUT)
    @PutMapping("/{idPoste}")
    public Post getPostByIdAndUpdate(@PathVariable long idPost, @RequestBody PostPayload payload) {
        return this.postService.findByIdAndUpdate(idPost, payload);
    }

    //ELIMINA LO SPECIFICO POST (DELETE)
    @DeleteMapping("/{idPost}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void getAPostByIdAndDelete(@PathVariable long idPost) {
        this.postService.findByIdAndDelete(idPost);
    }
}

