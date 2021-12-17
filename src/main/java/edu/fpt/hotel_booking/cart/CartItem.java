package edu.fpt.hotel_booking.cart;

import edu.fpt.hotel_booking.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CartItem {

    private Room room;
    private int amount;
    private Date checkinDate;
    private Date checkoutDate;
}
