package misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Inventory
{
    private final LinkedList<Item> items;
    private int inventoryWeight;
    private int maxWeight;

    public Inventory(int maxWeight)
    {
        this.items = new LinkedList<>();
        this.maxWeight = maxWeight;
        this.inventoryWeight = 0;
    }

    public void insertItem(Item item)
    {
        if (this.maxWeight == -1 || this.inventoryWeight + item.getItemWeight() < maxWeight) {
            this.items.add(item);
            this.onUpdate();
        }
    }

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

    public void transferTo(Inventory otherInventory, String itemName)
    {
        Item itemToTransfer = this.takeItem(itemName);

        if (itemToTransfer != null) {
            otherInventory.insertItem(itemToTransfer);
        }
    }

    public boolean hasItem(String itemName)
    {
        for (Item item : this.items) {
            if (item.getItemName().equals(itemName)) {
                return (true);
            }
        }
        return (false);
    }

    public int getMaxWeight()
    {
        return (this.maxWeight);
    }

    public void setMaxWeight(int maxWeight)
    {
        this.maxWeight = maxWeight;
    }

    public void onUpdate()
    {
        this.inventoryWeight = 0;

        for (Item item : this.items) {
            this.inventoryWeight += item.getItemWeight();
        }
    }

    public LinkedList<Item> getItems()
    {
        return (this.items);
    }

    public int getInventoryWeight()
    {
        return inventoryWeight;
    }
}
