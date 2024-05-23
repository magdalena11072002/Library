package my_library.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    
    @Column(name = "author")
    private String author;

    @Column(name="genre")
    private String genre; //gatunek książki

    @Column(name = "amount")
    private int amount;





}
