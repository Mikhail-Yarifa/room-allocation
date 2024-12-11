package com.example.room.service.room_allocation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AllocationServiceTest {

  public static final List<Double> POTENTIONAL_GUESTS = Arrays.asList(23.0, 45.0, 155.0, 374.0,
      22.0, 99.99, 100.0,
      101.0, 115.0, 209.0);
  private AllocationRequest allocationRequest;

  @Autowired
  private AllocationService allocationService;

  @Test
  void allocateLessRoomsThenProposition() {
    allocationRequest = new AllocationRequest(3, 3, POTENTIONAL_GUESTS);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(3, allocationResult.usagePremium());
    assertEquals(3, allocationResult.usageEconomy());
    assertEquals(738, allocationResult.revenuePremium());
    assertEquals(167.99, allocationResult.revenueEconomy());
  }

  @Test
  void allocateMoreRoomsThenProposition() {
    allocationRequest = new AllocationRequest(7, 5, POTENTIONAL_GUESTS);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(6, allocationResult.usagePremium());
    assertEquals(4, allocationResult.usageEconomy());
    assertEquals(1054, allocationResult.revenuePremium());
    assertEquals(189.99, allocationResult.revenueEconomy());
  }

  @Test
  void allocateMoreEconomyThanPremium() {
    allocationRequest = new AllocationRequest(2, 7, POTENTIONAL_GUESTS);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(2, allocationResult.usagePremium());
    assertEquals(4, allocationResult.usageEconomy());
    assertEquals(583, allocationResult.revenuePremium());
    assertEquals(189.99, allocationResult.revenueEconomy());
  }

  @Test
  void allocateEqualsAsPropositions() {
    allocationRequest = new AllocationRequest(6, 4, POTENTIONAL_GUESTS);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(6, allocationResult.usagePremium());
    assertEquals(4, allocationResult.usageEconomy());
    assertEquals(1054, allocationResult.revenuePremium());
    assertEquals(189.99, allocationResult.revenueEconomy());
  }

  @Test
  void allocateEconomyToPremium() {
    allocationRequest = new AllocationRequest(7, 2, POTENTIONAL_GUESTS);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(7, allocationResult.usagePremium());
    assertEquals(2, allocationResult.usageEconomy());
    assertEquals(1153.99, allocationResult.revenuePremium());
    assertEquals(68.0, allocationResult.revenueEconomy());
  }
}