package com.example.room.service.room_allocation.controller;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import com.example.room.service.room_allocation.dto.AllocationResponse;
import com.example.room.service.room_allocation.service.AllocationService;
import jakarta.validation.Valid;
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
  public AllocationResponse allocate(@RequestBody @Valid AllocationRequest request) {
    return allocationService.allocate(request);
  }

}
