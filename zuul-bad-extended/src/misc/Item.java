package misc;

public class Item
{
    private String itemName;
    private Integer itemWeight;

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
