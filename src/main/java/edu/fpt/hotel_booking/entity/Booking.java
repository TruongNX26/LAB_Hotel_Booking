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
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "booking_date")
    private Date bookingDate;

    private long total;

    private int discount;

    private int rating;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int confirm;

    public enum Status {
        CONFIRMING, ACTIVE, INACTIVE, CHECKED_OUT
    }

}
