package pl.nauka5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "AUTHORS")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author: first name can't be empty")
    @Column(length = 40)
    @Length(max = 40, message = "Author: first name can't be longer than 40 characters")
    private String firstName;

    @NotBlank(message = "Author: last name can't be empty")
    @Column(length = 50)
    @Length(max = 50, message = "Author: last name can't be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "Author: nationality can't be empty")
    @Column(length = 30)
    @Length(max = 30, message = "Author: nationality can't be longer than 30 characters")
    private String nationality;
}
