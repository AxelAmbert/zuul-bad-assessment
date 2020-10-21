package misc;

import java.util.Random;

public class Observer
{
    private final int observerID;

    public Observer()
    {
      Random random = new Random();

      this.observerID = random.nextInt();
    }

    public boolean equals(Object object)
    {
      Observer otherObserver;

      if (object instanceof Observer == false) {
        return (false);
      }
      otherObserver = (Observer)object;
      return (otherObserver.observerID == this.observerID);
    }

    public void onUpdate(Object object)
    {

    }
}
