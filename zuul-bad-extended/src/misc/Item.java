package misc;

/**
 * Class Item - an object.
 * <p>
 * The Item class can be used to represent any object.
 * It doesn't have any related logic, but feel free to add some
 * by inheriting this class.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class Item
{
  private final String itemName;
  private final Integer itemWeight;

  /**
   * Create an Item instance.
   *
   * @param itemName the item name
   * @param itemWeight the item weight
   */
  public Item(String itemName, Integer itemWeight)
  {
    this.itemName = itemName;
    this.itemWeight = itemWeight;
  }

  /**
   * Get the item name.
   *
   * @return the item name
   */
  public String getItemName()
  {
    return itemName;
  }

  /**
   * Get the item weight.
   *
   * @return the item weight
   */
  public Integer getItemWeight()
  {
    return itemWeight;
  }

}
