package misc;


/**
 * Class to handle the World Loading options
 * No logic is performed, it's only purpose is to store data.
 * @author Axel Ambert
 * @version 1.0
 */
public class CreationOptions
{
  private Item baseItem;
  private boolean removeNoExit;
  private boolean removeNoItem;
  private boolean addItem;
  private String filePath;

  public CreationOptions(String filePath, Item item, boolean noExit, boolean noItem)
  {
    this.addItem = item != null;
    this.filePath = filePath;
    this.baseItem = item;
    this.removeNoExit = noExit;
    this.removeNoItem = noItem;
  }

  public Item getBaseItem()
  {
    return baseItem;
  }

  public boolean isRemoveNoExit()
  {
    return removeNoExit;
  }

  public boolean isRemoveNoItem()
  {
    return removeNoItem;
  }

  public boolean isAddItem()
  {
    return addItem;
  }

  public String getFilePath()
  {
    return filePath;
  }

  public void setFilePath(String filePath)
  {
    this.filePath = filePath;
  }
}

