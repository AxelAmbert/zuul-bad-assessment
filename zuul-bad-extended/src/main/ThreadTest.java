package main;

public class ThreadTest implements Runnable
{

    @Override
    public void run()
    {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("mdr");
        }
    }
}
