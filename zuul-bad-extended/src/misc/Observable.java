package misc;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Observable
{
  public LinkedList<Observer> observers;

  public Observable()
  {
    this.observers = new LinkedList<>();
  }

  public void addObserver(Observer observerToAdd)
  {
    this.observers.add(observerToAdd);
  }

  public void removeObserver(Observer observerToRemove)
  {
    for (Iterator<Observer> it = this.observers.iterator(); it.hasNext(); ) {
      Observer observer =  it.next();

      if (observer.equals(observerToRemove)) {
        it.remove();
        return;
      }
    }
  }

  public void update()
  {
    this.observers.forEach((observer) -> {
      observer.onUpdate(this);
    });
  }
}
