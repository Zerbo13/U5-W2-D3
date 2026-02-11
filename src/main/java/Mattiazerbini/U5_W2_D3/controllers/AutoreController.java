package Mattiazerbini.U5_W2_D3.controllers;

import Mattiazerbini.U5_W2_D3.entities.Autore;
import Mattiazerbini.U5_W2_D3.entities.Post;
import Mattiazerbini.U5_W2_D3.payloads.AutorePayload;
import Mattiazerbini.U5_W2_D3.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autore")
public class AutoreController {

    private final AutoreService autoreService;

    @Autowired
    public AutoreController(AutoreService autoreService){
        this.autoreService = autoreService;
    }

    //LISTA DI AUTORI (GET)
    @GetMapping
    public Page<Autore> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "surname") String orderBy,
                              @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.autoreService.findAll(page, size, orderBy, sortCriteria);
    }


    //RITORNA UN SINGOLO AUTORE (GET)
    @GetMapping("/{idAutore}")
    public Autore getAutoreById(@PathVariable long idAutore) {
        return this.autoreService.findById(idAutore);
    }

    //CREAZIONE DI UN NUOVO AUTORE (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Autore createAutore(@RequestBody AutorePayload payload) {
        return this.autoreService.salvaAutore(payload);
    }


    //MODIFICA LO SPECIFICO AUTORE (PUT)
    @PutMapping("/{idAutore}")
    public Autore getAutoreByIdAndUpdate(@PathVariable long idAutore, @RequestBody AutorePayload payload) {
        return this.autoreService.findByIdAndUpdate(idAutore, payload);
    }

    //ELIMINA LO SPECIFICO AUTORE (DELETE)
    @DeleteMapping("/{idAutore}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void getAutoreByIdAndDelete(@PathVariable long idAutore) {
        this.autoreService.findByIdAndDelete(idAutore);
    }
}

