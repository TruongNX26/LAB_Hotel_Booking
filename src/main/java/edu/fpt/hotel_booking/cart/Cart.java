package edu.fpt.hotel_booking.cart;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private final Map<Long, CartItem> items = new HashMap<>();

    public void add(CartItem newItem) {
        CartItem item = items.get(newItem.getRoom().getId());
        if (item == null) {
            items.put(newItem.getRoom().getId(), newItem);
        } else {
            int amount = newItem.getAmount() + item.getAmount();
            item.setAmount(amount);
            items.put(item.getRoom().getId(), item);
        }
    }

    public void updateAmount(long roomId, int amount) {
        CartItem item = items.get(roomId);
        if (item != null) {
            item.setAmount(amount);
        }
    }

    public Map<Long, CartItem> getItems() {
        return items;
    }

    public void remove(long roomId) {
        items.remove(roomId);
    }

    public long calculateTotal() {
        long total = 0;
        for (CartItem item : items.values()) {
            total += (long) item.getAmount() * (long) item.getRoom().getRoomType().getPrice();
        }

        return total;
    }
}