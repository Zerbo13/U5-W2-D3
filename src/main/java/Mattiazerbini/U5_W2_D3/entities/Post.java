package Mattiazerbini.U5_W2_D3.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private int tempoDiLettura;

    @ManyToOne
    @JoinColumn(name = "id_autore")
    private Autore autore;

    public Post(String categoria, String titolo, String contenuto, int tempoDiLettura) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
        this.cover = "https://picsum.photos/200/300";
    }
}
