package Mattiazerbini.U5_W2_D3.services;

import Mattiazerbini.U5_W2_D3.entities.Autore;

import Mattiazerbini.U5_W2_D3.exception.NotFoundException;
import Mattiazerbini.U5_W2_D3.payloads.AutorePayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AutoreService {

    private List<Autore> autoreDB = new ArrayList<>();

    public List<Autore> findAll(){
        return this.autoreDB;
    }

    public Autore salvaAutore(AutorePayload payload){
        Autore newAutore = new Autore(payload.getNome(), payload.getCognome(),
                payload.getEmail(), payload.getDataDiNascita());
        this.autoreDB.add(newAutore);
        log.info("L'utente "+newAutore.getNome()+ newAutore.getCognome()+ " è stato inserito!");
        return newAutore;
    }

    public Autore findById(long idAutore){
        Autore find = null;
        for (Autore user : this.autoreDB) {
            if (user.getId() == idAutore) find = user;
        }
        if (find == null) throw new NotFoundException(idAutore);
        return find;
    }

    public Autore findByIdAndUpdate(long idAutore, AutorePayload payload) {
        Autore find = null;
        for (Autore autore : this.autoreDB) {
            if (autore.getId() == idAutore) {
                find = autore;
                find.setNome(payload.getNome());
                find.setCognome(payload.getCognome());
                find.setEmail(payload.getEmail());
                find.setDataDiNascita(payload.getDataDiNascita());
            }
        }
        if (find == null) throw new NotFoundException(idAutore);
        return find;
    }

    public void findByIdAndDelete(long idAutore) {
        Autore find = null;
        for (Autore autore : this.autoreDB) {
            if (autore.getId() == idAutore) find = autore;
        }
        if (find == null) throw new NotFoundException(idAutore);
        this.autoreDB.remove(find);
    }
}
