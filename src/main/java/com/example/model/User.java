package com.example.model;

import com.example.model.enums.Gender;
import com.example.model.enums.Goal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    // TODO encrypt data
    @Column(nullable = false)
    private String password;

    @Min(18)
    @Max(120)
    private int age;

    @Min(30)
    @Max(300)
    private double weight;

    @Min(100)
    @Max(250)
    private double height;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double dailyCalories;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;
}
