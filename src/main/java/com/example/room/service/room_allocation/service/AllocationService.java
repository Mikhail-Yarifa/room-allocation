package com.example.room.service.room_allocation.service;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import com.example.room.service.room_allocation.dto.AllocationResult;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AllocationService {

  public AllocationResult allocate(AllocationRequest request) {
    var proposedPrices = request.getPotentialGuests();
    Collections.sort(proposedPrices);

    List<Double> premiumPrices = proposedPrices.stream()
        .filter(num -> num >= 100)
        .toList();
    List<Double> economyPrices = proposedPrices.stream()
        .filter(num -> num < 100)
        .toList();
    return performAllocationResult(request, premiumPrices, economyPrices);
  }

  private AllocationResult performAllocationResult(AllocationRequest request,
      List<Double> premiumPrices, List<Double> economyPrices) {
    if (request.getEconomyRooms() >= economyPrices.size()
        && request.getPremiumRooms()
        >= premiumPrices.size()) { //rooms more than propositions - count full sum from 2 lists
      var premiumSum = premiumPrices.stream().mapToDouble(Double::doubleValue).sum();
      var economySum = economyPrices.stream().mapToDouble(Double::doubleValue).sum();
      return new AllocationResult(
          premiumPrices.size(), premiumSum, economyPrices.size(), economySum);
    } else if (request.getEconomyRooms() < economyPrices.size()
        && request.getPremiumRooms()
        < premiumPrices.size()) { //rooms less than propositions - count maximums from 2 lists
      int start = premiumPrices.size() - request.getPremiumRooms();
      var premiumSum = premiumPrices.subList(start, premiumPrices.size())
          .stream().mapToDouble(Double::doubleValue).sum();
      start = economyPrices.size() - request.getEconomyRooms();
      var economySum = economyPrices.subList(start, economyPrices.size())
          .stream().mapToDouble(Double::doubleValue).sum();
      return new AllocationResult(
          request.getPremiumRooms(), premiumSum, request.getEconomyRooms(), economySum);
    } else if (request.getEconomyRooms() > economyPrices.size()
        && request.getPremiumRooms()
        < premiumPrices.size()) { //economy rooms more that premium rooms - count maximums from premium and full economy list
      int start = premiumPrices.size() - request.getPremiumRooms();
      var premiumSum = premiumPrices.subList(start, premiumPrices.size())
          .stream().mapToDouble(Double::doubleValue).sum();
      var economySum = economyPrices.stream().mapToDouble(Double::doubleValue).sum();
      return new AllocationResult(
          request.getPremiumRooms(), premiumSum, economyPrices.size(), economySum);
    } else if (request.getEconomyRooms() < economyPrices.size()
        && request.getPremiumRooms()
        > premiumPrices.size()) { //economy less than premium - count full premium plus maximums from economy and remaining maximums from economy
      var premiumSum = premiumPrices.stream().mapToDouble(Double::doubleValue).sum();
      var remainingPremium = request.getPremiumRooms() - premiumPrices.size();
      var additionalPremiumSum = economyPrices.subList(economyPrices.size() - remainingPremium,
              economyPrices.size())
          .stream().mapToDouble(Double::doubleValue).sum();
      int start = economyPrices.size() - remainingPremium - request.getEconomyRooms();
      var economySum = economyPrices.subList(start, economyPrices.size() - remainingPremium)
          .stream().mapToDouble(Double::doubleValue).sum();
      return new AllocationResult(
          request.getPremiumRooms(), premiumSum + additionalPremiumSum, request.getEconomyRooms(),
          economySum);
    } else {
      return new AllocationResult(0, 0.0, 0, 0.0);
    }
  }

}
