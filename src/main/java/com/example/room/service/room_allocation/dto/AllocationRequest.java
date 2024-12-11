package com.example.room.service.room_allocation.dto;

import java.util.List;

public record AllocationRequest(int premiumRooms, int economyRooms, List<Double> potentialGuests) {

}
