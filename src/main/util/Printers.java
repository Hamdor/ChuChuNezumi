package main.util;

import com.pokegoapi.api.map.fort.PokestopLootResult;

import POGOProtos.Data.Capture.CaptureProbabilityOuterClass.CaptureProbability;
import POGOProtos.Inventory.Item.ItemAwardOuterClass.ItemAward;

public class Printers {
  
  public static void print(CaptureProbability prob) {
    System.out.println("Chances are: ");
    for (Float f : prob.getCaptureProbabilityList())
      System.out.print(f + "     ");
    System.out.println("");
  }
  
  public static void print(PokestopLootResult loot) {
    System.out.println("Looted Pokestop");
    System.out.println("EP: " + loot.getExperience());
    System.out.print("Items: ");
    for (ItemAward item : loot.getItemsAwarded())
      System.out.print("    " + item.getItemId() + ", Count: " + item.getItemCount());
    System.out.println("");
  }
}
