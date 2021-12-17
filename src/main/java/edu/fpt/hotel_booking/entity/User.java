package edu.fpt.hotel_booking.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(nullable = false)
    private boolean active;

    private String address;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String phone, String name, Date creationDate, String address, Role role) {
        this.phone = phone;
        this.name = name;
        this.creationDate = creationDate;
        this.address = address;
        this.role = role;
    }

    public enum Role {
        CUSTOMER, ADMIN
    }
}
