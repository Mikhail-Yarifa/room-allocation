package com.example.room.service.room_allocation.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record AllocationRequest(@Positive int premiumRooms, @Positive int economyRooms,
                                @Size(min = 1) List<@Positive Double> potentialGuests) {

}
