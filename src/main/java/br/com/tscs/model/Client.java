package br.com.tscs.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_client")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String document;
    private String phoneNumber;

    @Embedded
    private Address address;

}
