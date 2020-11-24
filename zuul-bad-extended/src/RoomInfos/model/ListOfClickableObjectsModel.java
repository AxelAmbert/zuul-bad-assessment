package RoomInfos.model;

import misc.Observable;
import misc.Observer;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ListOfClickableObjectsModel<Iterable> implements Observable
{
  private ArrayList<String[]> infos;
  private ArrayList<Observer> observers;

  public ListOfClickableObjectsModel()
  {
    this.observers = new ArrayList<>();
    this.infos = new ArrayList<>();
  }

  public void updateModel(Stream<Iterable> stream)
  {
    this.infos.clear();
    stream.forEach(this::getUsefulVariables);
    this.update();
  }

  private void getUsefulVariables(Iterable toIter)
  {
    String visualRepresentation = null;
    String name = null;
    String description = null;

    try {
      visualRepresentation = (String)toIter.getClass().getMethod("getVisualRepresentation").invoke(toIter);
      name = (String)toIter.getClass().getMethod("getName").invoke(toIter);
      description = (String)toIter.getClass().getMethod("getDescription").invoke(toIter);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    this.infos.add(new String[]{visualRepresentation, name, description});
  }

  public ArrayList<String[]> getInfos()
  {
    return (this.infos);
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
    this.observers.stream().forEach(observer -> observer.onUpdate(this.infos));
  }
}
