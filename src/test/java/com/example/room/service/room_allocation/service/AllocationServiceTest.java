package com.example.room.service.room_allocation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AllocationServiceTest {

  private AllocationRequest allocationRequest;

  @Autowired
  private AllocationService allocationService;

  @BeforeEach
  void setup() {
    List<Double> potentionalGuests = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0,
        101.0, 115.0, 209.0);
    allocationRequest = new AllocationRequest(0, 0, potentionalGuests);
  }

  @Test
  void allocateLessRoomsThenProposition() {
    allocationRequest.setEconomyRooms(3);
    allocationRequest.setPremiumRooms(3);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(3, allocationResult.getUsagePremium());
    assertEquals(3, allocationResult.getUsageEconomy());
    assertEquals(738, allocationResult.getRevenuePremium());
    assertEquals(167.99, allocationResult.getRevenueEconomy());
  }

  @Test
  void allocateMoreRoomsThenProposition() {
    allocationRequest.setEconomyRooms(5);
    allocationRequest.setPremiumRooms(7);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(6, allocationResult.getUsagePremium());
    assertEquals(4, allocationResult.getUsageEconomy());
    assertEquals(1054, allocationResult.getRevenuePremium());
    assertEquals(189.99, allocationResult.getRevenueEconomy());
  }

  @Test
  void allocateMoreEconomyThanPremium() {
    allocationRequest.setEconomyRooms(7);
    allocationRequest.setPremiumRooms(2);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(2, allocationResult.getUsagePremium());
    assertEquals(4, allocationResult.getUsageEconomy());
    assertEquals(583, allocationResult.getRevenuePremium());
    assertEquals(189.99, allocationResult.getRevenueEconomy());
  }

  @Test
  void allocateEqualsAsPropositions() {
    allocationRequest.setEconomyRooms(4);
    allocationRequest.setPremiumRooms(6);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(6, allocationResult.getUsagePremium());
    assertEquals(4, allocationResult.getUsageEconomy());
    assertEquals(1054, allocationResult.getRevenuePremium());
    assertEquals(189.99, allocationResult.getRevenueEconomy());
  }

  @Test
  void allocateEconomyToPremium() {
    allocationRequest.setEconomyRooms(2);
    allocationRequest.setPremiumRooms(7);
    var allocationResult = allocationService.allocate(allocationRequest);
    assertEquals(7, allocationResult.getUsagePremium());
    assertEquals(2, allocationResult.getUsageEconomy());
    assertEquals(1153.99, allocationResult.getRevenuePremium());
    assertEquals(68.0, allocationResult.getRevenueEconomy());
  }
}