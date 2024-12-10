package com.example.room.service.room_allocation.dto;

import java.util.List;

public class AllocationRequest {

  private int premiumRooms;
  private int economyRooms;
  private List<Double> potentialGuests;

  public AllocationRequest(int premiumRooms, int economyRooms, List<Double> potentialGuests) {
    this.premiumRooms = premiumRooms;
    this.economyRooms = economyRooms;
    this.potentialGuests = potentialGuests;
  }

  public int getPremiumRooms() {
    return premiumRooms;
  }

  public void setPremiumRooms(int premiumRooms) {
    this.premiumRooms = premiumRooms;
  }

  public int getEconomyRooms() {
    return economyRooms;
  }

  public void setEconomyRooms(int economyRooms) {
    this.economyRooms = economyRooms;
  }

  public List<Double> getPotentialGuests() {
    return potentialGuests;
  }

  public void setPotentialGuests(List<Double> potentialGuests) {
    this.potentialGuests = potentialGuests;
  }

  @Override
  public String toString() {
    return "AllocationRequest{" +
        "premiumRooms=" + premiumRooms +
        ", economyRooms=" + economyRooms +
        ", potentialGuests=" + potentialGuests +
        '}';
  }
}
