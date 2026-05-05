package pl.nauka5.dto;

import lombok.Data;

@Data
public class AuthorAdminDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
}
