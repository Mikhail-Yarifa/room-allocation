package com.example.room.service.room_allocation.controller;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import com.example.room.service.room_allocation.dto.AllocationResult;
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
  public AllocationResult allocate(@RequestBody AllocationRequest request) {
    if (request.getPremiumRooms() <= 0 && request.getEconomyRooms() <= 0
        || request.getPotentialGuests().isEmpty()) {
      return new AllocationResult(0, 0.0, 0, 0.0);
    }
    return allocationService.allocate(request);
  }

}
