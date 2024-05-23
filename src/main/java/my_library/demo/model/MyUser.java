package my_library.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "my_user")
    private String myUser;

    @Column(name = "amountOfBooks")
    private int amountOfBooks;

    @Column(name = "interest")
    private Long interest; //odsetki
}
