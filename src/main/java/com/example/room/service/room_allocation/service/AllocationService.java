package com.example.room.service.room_allocation.service;

import com.example.room.service.room_allocation.dto.AllocationRequest;
import com.example.room.service.room_allocation.dto.AllocationResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AllocationService {

  @Value("${app.economyLimitPrice}")
  private int economyLimitPrice;

  public AllocationResponse allocate(AllocationRequest request) {
    var proposedPrices = request.potentialGuests();
    Collections.sort(proposedPrices);

    Map<Boolean, List<Double>> partitionedByPrice =
        proposedPrices.stream()
            .collect(Collectors.partitioningBy(price -> price >= economyLimitPrice));

    List<Double> premiumPrices = partitionedByPrice.get(true);
    List<Double> economyPrices = partitionedByPrice.get(false);

    return performAllocationResult(request, premiumPrices, economyPrices);
  }

  private AllocationResponse performAllocationResult(AllocationRequest request,
      List<Double> premiumPrices, List<Double> economyPrices) {
    if (request.economyRooms() >= economyPrices.size()
        && request.premiumRooms()
        >= premiumPrices.size()) { //rooms more than propositions - count full sum from 2 lists
      return new AllocationResponse(premiumPrices.size(), calculateSum(premiumPrices),
          economyPrices.size(), calculateSum(economyPrices));
    } else if (request.economyRooms() < economyPrices.size()
        && request.premiumRooms()
        < premiumPrices.size()) { //rooms less than propositions - count maximums from 2 lists
      int startPremium = premiumPrices.size() - request.premiumRooms();
      int startEconomy = economyPrices.size() - request.economyRooms();
      return new AllocationResponse(request.premiumRooms(),
          calculateSumOfSubList(premiumPrices, startPremium),
          request.economyRooms(), calculateSumOfSubList(economyPrices, startEconomy));
    } else if (request.economyRooms()
        > economyPrices.size()) { //economy rooms more that premium rooms - count maximums from premium and full economy list
      int startPremium = premiumPrices.size() - request.premiumRooms();
      return new AllocationResponse(request.premiumRooms(),
          calculateSumOfSubList(premiumPrices, startPremium),
          economyPrices.size(), calculateSum(economyPrices));
    } else if (request.economyRooms() < economyPrices.size()
        && request.premiumRooms()
        > premiumPrices.size()) { //economy less than premium - count full premium plus maximums from economy and remaining maximums from economy
      return allocateEconomyToPremium(request, premiumPrices, economyPrices);
    } else {
      return new AllocationResponse(0, 0.0, 0, 0.0);
    }
  }

  private AllocationResponse allocateEconomyToPremium(AllocationRequest request,
      List<Double> premiumPrices, List<Double> economyPrices) {
    var remainingPremium = request.premiumRooms() - premiumPrices.size();
    var additionalPremiumSum = calculateSum(
        economyPrices.subList(economyPrices.size() - remainingPremium,
            economyPrices.size()));
    int start = economyPrices.size() - remainingPremium - request.economyRooms();
    var economySum = calculateSum(
        economyPrices.subList(start, economyPrices.size() - remainingPremium));
    return new AllocationResponse(
        request.premiumRooms(), calculateSum(premiumPrices) + additionalPremiumSum,
        request.economyRooms(),
        economySum);
  }

  private double calculateSum(List<Double> prices) {
    return prices.stream().mapToDouble(Double::doubleValue).sum();
  }

  private double calculateSumOfSubList(List<Double> prices, int start) {
    return prices.subList(start, prices.size()).stream().mapToDouble(Double::doubleValue).sum();
  }

}
