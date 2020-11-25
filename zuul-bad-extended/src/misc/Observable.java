package misc;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Implement this class to inform that your class is Observable.
 * @author Axel Ambert
 * @version 1.0
 */

public interface Observable
{
  /**
   * Add an observer to a list of observers
   * @param observerToAdd observer to add
   */
  public void addObserver(Observer observerToAdd);

  /**
   * remover an observer to a list of observers
   * @param observerToRemove observer to remove
   */
  public void removeObserver(Observer observerToRemove);

  /**
   * Call this function when a particular event is done in your class.
   * To inform the other class.
   */
  public void update();
}
