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
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "checkin_date")
    private Date checkinDate;

    @Column(name = "checkout_date")
    private Date checkoutDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private int price;

    private int amount;
}
