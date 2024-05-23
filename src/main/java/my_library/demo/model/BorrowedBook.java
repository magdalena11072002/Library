package my_library.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "borrowed_books") //wypożyczone ksiażki
@Getter
@Setter
@NoArgsConstructor
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "my_user")
    private String myUser;

    @Column(name = "rentalDate")
    private LocalDate rentalDate; //data wypożyczenia

    @Column(name = "dateOfSubmission")
    private LocalDate dateOfSubmission; //data zwrotu
}
