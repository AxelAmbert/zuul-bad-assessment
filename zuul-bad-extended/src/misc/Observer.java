package misc;

import java.util.Random;

public class Observer
{
    private final int observerID;
    private OneArgObjectInterface toRun;

    public Observer(OneArgObjectInterface runnable)
    {
      Random random = new Random();

      this.toRun = runnable;
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
      this.toRun.run(object);
    }
}
