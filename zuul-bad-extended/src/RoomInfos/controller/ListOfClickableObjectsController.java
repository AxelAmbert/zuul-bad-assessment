package RoomInfos.controller;

import RoomInfos.model.ListOfClickableObjectsModel;
import misc.Observable;
import misc.Observer;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ListOfClickableObjectsController<Iterable> implements Observable
{
  ListOfClickableObjectsModel<Iterable> model;
  String storedValue;
  ArrayList<Observer> observers;

  public ListOfClickableObjectsController(ListOfClickableObjectsModel<Iterable> model)
  {
    this.observers = new ArrayList<>();
    this.storedValue = "";
    this.model = model;
  }

  public void updateController(Stream<Iterable> stream)
  {
    this.model.updateModel(stream);
  }

  public ListOfClickableObjectsModel<Iterable> getModel()
  {
    return model;
  }

  public String getStoredValue()
  {
    return storedValue;
  }

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