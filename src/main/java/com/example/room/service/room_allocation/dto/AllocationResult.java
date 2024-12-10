package com.example.room.service.room_allocation.dto;

public class AllocationResult {

  private int usagePremium;
  private double revenuePremium;
  private int usageEconomy;
  private double revenueEconomy;

  public AllocationResult(int usagePremium, double revenuePremium, int usageEconomy,
      double revenueEconomy) {
    this.usagePremium = usagePremium;
    this.revenuePremium = revenuePremium;
    this.usageEconomy = usageEconomy;
    this.revenueEconomy = revenueEconomy;
  }

  public int getUsagePremium() {
    return usagePremium;
  }

  public void setUsagePremium(int usagePremium) {
    this.usagePremium = usagePremium;
  }

  public double getRevenuePremium() {
    return revenuePremium;
  }

  public void setRevenuePremium(double revenuePremium) {
    this.revenuePremium = revenuePremium;
  }

  public int getUsageEconomy() {
    return usageEconomy;
  }

  public void setUsageEconomy(int usageEconomy) {
    this.usageEconomy = usageEconomy;
  }

  public double getRevenueEconomy() {
    return revenueEconomy;
  }

  public void setRevenueEconomy(double revenueEconomy) {
    this.revenueEconomy = revenueEconomy;
  }

  @Override
  public String toString() {
    return "AllocationResult{" +
        "usagePremium=" + usagePremium +
        ", revenuePremium=" + revenuePremium +
        ", usageEconomy=" + usageEconomy +
        ", revenueEconomy=" + revenueEconomy +
        '}';
  }
}