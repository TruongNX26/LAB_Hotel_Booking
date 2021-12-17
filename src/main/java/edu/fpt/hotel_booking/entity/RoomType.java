package edu.fpt.hotel_booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomType {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    private String name;

    private int price;
}
