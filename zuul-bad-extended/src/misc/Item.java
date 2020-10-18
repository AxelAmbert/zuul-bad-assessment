package misc;

public class Item
{
  private final String itemName;
  private final Integer itemWeight;

  public Item(String itemName, Integer itemWeight)
  {
    this.itemName = itemName;
    this.itemWeight = itemWeight;
  }

  public String getItemName()
  {
    return itemName;
  }

  public Integer getItemWeight()
  {
    return itemWeight;
  }

}
