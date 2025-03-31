package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
public class Contact extends BaseEntity {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^\\+\\d{1,3}\\d{1,14}$", message = "Invalid phone number format")
    private String phoneNumber;

    @OneToOne(mappedBy = "contact")
    private User client;

    /*
    and others  address ...
     */
}
