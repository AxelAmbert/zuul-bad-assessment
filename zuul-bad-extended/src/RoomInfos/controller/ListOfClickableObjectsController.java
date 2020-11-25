package RoomInfos.controller;

import RoomInfos.model.ListOfClickableObjectsModel;
import misc.Observable;
import misc.Observer;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Class ListOfClickableObjectController - the controller of a list of clickable objects.
 * This class is the controller of the ListOfClickableObject object
 * which is a list of clickable objects
 *
 * @author Axel Ambert
 * @version 1.0
 */


public class ListOfClickableObjectsController<Iterable> implements Observable
{
  ListOfClickableObjectsModel<Iterable> model;
  String storedValue;
  ArrayList<Observer> observers;

  /**
   * Constructor of the ListofClickableObjectController
   * @param model related MVC model
   */
  public ListOfClickableObjectsController(ListOfClickableObjectsModel<Iterable> model)
  {
    this.observers = new ArrayList<>();
    this.storedValue = "";
    this.model = model;
  }

  /**
   * Update the controller
   * @param stream stream that will update the controller
   */
  public void updateController(Stream<Iterable> stream)
  {
    this.model.updateModel(stream);
  }

  /**
   * Get the related model
   * @return the related model
   */
  public ListOfClickableObjectsModel<Iterable> getModel()
  {
    return model;
  }

  /**
   * Get the stored value
   * The stored value is the one related to the button on which
   * the user clicks.
   * @return the stored value
   */
  public String getStoredValue()
  {
    return storedValue;
  }

  /**
   * Set the stored value.
   * @param storedValue the stored value to set
   */
  public void setStoredValue(String storedValue)
  {
    this.storedValue = storedValue;
    this.update();
  }

  @Override
  public void addObserver(Observer observerToAdd)
  {
    this.observers.add(observerToAdd);
  }

  @Override
  public void removeObserver(Observer observerToRemove)
  {
    this.observers.remove(observerToRemove);
  }

  @Override
  public void update()
  {
    this.observers.stream().forEach(observer -> observer.onUpdate(this.storedValue));
  }
}