package misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Inventory - a bag full of item.
 * <p>
 * The Inventory is an easy way to handle items through the game.
 * It is a list a list of items that keep track on the total weight,
 * it is also useful to handle transfer between two inventory.
 * <p>
 * Even if this class sound like something you would only put on a player,
 * feel free to implement it on every object that need a list of object
 * (like a Room for example...)
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Inventory implements Observable
{
  private final LinkedList<Item> items;
  private int inventoryWeight;
  private int maxWeight;
  private ArrayList<Observer> observersList;

  /**
   * Create an Inventory instance.
   * Set maxWeight to -1 if you want infinite weight.
   *
   * @param maxWeight the maximum weight inventory can handle.
   */
  public Inventory(int maxWeight)
  {
    this.items = new LinkedList<>();
    this.maxWeight = maxWeight;
    this.inventoryWeight = 0;
    this.observersList = new ArrayList<>();
  }

  /**
   * Insert an item into the inventory.
   * If the item weight + the current weight is superior to the
   * max weight, the operation is aborted.
   *
   * @param item the item to add in the list.
   */
  public void insertItem(Item item)
  {
    if (this.maxWeight == -1 || this.inventoryWeight + item.getItemWeight() < maxWeight) {
      this.items.add(item);
      this.onUpdate();
    }
  }

  /**
   * Take out an item from the inventory and update
   * the weight accordingly.
   *
   * If the item is not in the inventory, return null
   *
   * @param itemName the item to take
   * @return the item, or null if not found
   */
  public Item takeItem(String itemName)
  {
    Iterator<Item> iterator = this.items.iterator();

    while (iterator.hasNext()) {
      Item item = iterator.next();
      if (item.getItemName().equals(itemName)) {
        iterator.remove();
        this.onUpdate();
        return (item);
      }
    }
    return (null);
  }

  /**
   * Transfer an item from this inventory into another one.
   * This function uses the same rules as :
   * takeItem(String itemName)
   * insertItem(Item item)
   *
   * @param otherInventory the inventory where to add objects
   * @param itemName the item to transfer
   */
  public void transferTo(Inventory otherInventory, String itemName)
  {
    Item itemToTransfer = this.takeItem(itemName);

    if (itemToTransfer != null) {
      otherInventory.insertItem(itemToTransfer);
    }
  }

  /**
   * Find an item via its name in the item's list.
   *
   * @param itemName the item to find
   * @return true if the item is found, false otherwise
   */
  public boolean hasItem(String itemName)
  {
    for (Item item : this.items) {
      if (item.getItemName().equals(itemName)) {
        return (true);
      }
    }
    return (false);
  }

  /**
   * Get the max weight of the inventory.
   *
   * @return the max weight of the inventory.
   */
  public int getMaxWeight()
  {
    return (this.maxWeight);
  }

  /**
   * Set the max weight of the inventory.
   *
   * @param maxWeight the max weight of the inventory
   */
  public void setMaxWeight(int maxWeight)
  {
    this.maxWeight = maxWeight;
  }

  /**
   * This function is called everytime the inventory list is updated.
   * It set the real weight of the inventory when an item is added/removed.
   */
  public void onUpdate()
  {
    this.inventoryWeight = 0;

    for (Item item : this.items) {
      this.inventoryWeight += item.getItemWeight();
    }
    this.update();
  }

  /**
   * Get the (unmodifiable) list of objects.
   *
   * @return the (unmodifiable) list of objects
   */
  public LinkedList<Item> getItems()
  {
    return ((LinkedList<Item>)this.items.clone());
  }

  /**
   * Get the inventory weight.
   *
   * @return the inventory weight
   */
  public int getInventoryWeight()
  {
    return inventoryWeight;
  }

  @Override
  public void addObserver(Observer observerToAdd)
  {
    this.observersList.add(observerToAdd);
  }

  @Override
  public void removeObserver(Observer observerToRemove)
  {
    this.observersList.remove(observerToRemove);
  }

  @Override
  public void update()
  {
    this.observersList.stream().forEach((observer -> observer.onUpdate(this)));
  }
}
