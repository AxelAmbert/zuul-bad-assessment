package misc;

/**
 * Use this to create lambdas that get one Object arg and return nothing.
 * Currently used in the Observer class
 * @author Axel Ambert
 * @version 1.0
 */
public interface OneArgObjectInterface {

  /**
   * Run the lambda.
   * @param object an object that can be casted.
   */
  public void run(Object object);
}