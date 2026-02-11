package Mattiazerbini.U5_W2_D3.services;

import Mattiazerbini.U5_W2_D3.entities.Autore;

import Mattiazerbini.U5_W2_D3.entities.Post;
import Mattiazerbini.U5_W2_D3.exception.NotFoundException;
import Mattiazerbini.U5_W2_D3.exception.ValidationException;
import Mattiazerbini.U5_W2_D3.payloads.AutorePayload;
import Mattiazerbini.U5_W2_D3.repositories.AutoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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
public class AutoreService {

    private final AutoreRepository autoreRepository;

    @Autowired
    public AutoreService(AutoreRepository autoreRepository) {
        this.autoreRepository = autoreRepository;
    }

    public Autore salvaAutore(AutorePayload payload){
       if(this.autoreRepository.existsByEmail(payload.getEmail())){
            throw new ValidationException("Questa mail" + payload.getEmail() + "è gia registrata");
        }
       Autore newAutore = new Autore(payload.getNome(), payload.getCognome(),
                payload.getEmail(), payload.getDataDiNascita());
        newAutore.setAvatar("https://ui-avatars.com/api?name=" + payload.getNome() + "+" + payload.getCognome());
        Autore autoreSalvato = this.autoreRepository.save(newAutore);
        log.info("L'utente "+newAutore.getNome()+" " +newAutore.getCognome()+ " è stato inserito!");
        return autoreSalvato;
    }

    public Page<Autore> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.autoreRepository.findAll(pageable);
    }

    public Autore findById(long idAutore){
        return this.autoreRepository.findById(idAutore)
                .orElseThrow(() -> new NotFoundException(idAutore));
    }

    public Autore findByIdAndUpdate(long idAutore, AutorePayload payload) {
        Autore find = this.findById(idAutore);
        Autore autore = null;
        if (autore.getId() == idAutore) {
                find = autore;
                find.setNome(payload.getNome());
                find.setCognome(payload.getCognome());
                find.setEmail(payload.getEmail());
                find.setDataDiNascita(payload.getDataDiNascita());
            find.setAvatar("https://ui-avatars.com/api?name=" + payload.getNome() + "+" + payload.getCognome());
            }
        Autore autoreModificato = this.autoreRepository.save(find);
        log.info("L'utente "+autoreModificato.getId()+" è stato modificato");
        return autoreModificato;
    }

    public void findByIdAndDelete(long idAutore) {
        Autore find = this.findById(idAutore);
        this.autoreRepository.delete(find);
    }
}
