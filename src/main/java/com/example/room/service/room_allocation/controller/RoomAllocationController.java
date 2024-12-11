package com.example.room.service.room_allocation.controller;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import com.example.room.service.room_allocation.dto.AllocationResponse;
import com.example.room.service.room_allocation.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomAllocationController {

  @Autowired
  private final AllocationService allocationService;

  public RoomAllocationController(AllocationService allocationService) {
    this.allocationService = allocationService;
  }

  @PostMapping("/occupancy")
  public AllocationResponse allocate(@RequestBody AllocationRequest request) {
    if (isInvalidRequest(request)) {
      throw new IllegalArgumentException("Incorrect input");
    }
    if (isEmptyRequest(request)) {
      return new AllocationResponse(0, 0.0, 0, 0.0);
    }
    return allocationService.allocate(request);
  }

  private boolean isInvalidRequest(AllocationRequest request) {
    var isNegativePrices = request.potentialGuests().stream().anyMatch(num -> num < 0);
    return request.premiumRooms() < 0 || request.economyRooms() < 0 || isNegativePrices;
  }

  private boolean isEmptyRequest(AllocationRequest request) {
    return request.premiumRooms() == 0 && request.economyRooms() == 0 || request.potentialGuests()
        .isEmpty();
  }

}
