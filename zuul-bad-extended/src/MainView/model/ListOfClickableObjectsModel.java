package MainView.model;

import misc.Observable;
import misc.Observer;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Class ListOfClickableObjectController - the model of a list of clickable objects.
 * This class is the model of the ListOfClickableObject object
 * which is a list of clickable objects
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class ListOfClickableObjectsModel<Iterable> implements Observable
{
  private ArrayList<String[]> infos;
  private ArrayList<Observer> observers;

  /**
   * Constructor of the ListOfClickableObjectsModel
   */
  public ListOfClickableObjectsModel()
  {
    this.observers = new ArrayList<>();
    this.infos = new ArrayList<>();
  }

  /**
   * Update the Model thanks to a stream.
   * @param stream that will update the model
   */
  public void updateModel(Stream<Iterable> stream)
  {
    this.infos.clear();
    stream.forEach(this::getUsefulVariables);
    this.update();
  }

  /**
   * Get all the useful variables in the Iterable object thanks to reflection.
   * @param toIter object to iterate on.
   */
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

  /**
   * Get the infos variable
   * @return the infos variable
   */
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
