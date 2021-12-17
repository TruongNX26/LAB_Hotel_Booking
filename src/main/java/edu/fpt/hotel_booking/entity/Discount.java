package edu.fpt.hotel_booking.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Discount {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int percentage;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

}
