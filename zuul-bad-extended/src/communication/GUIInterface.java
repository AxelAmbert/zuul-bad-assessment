package communication;

import javax.swing.*;



public class GUIInterface implements Communication
{

  public GUIInterface()
  {
  }

  @Override
  public void showMessage(String toShow)
  {
    JOptionPane.showMessageDialog(null, toShow);
  }

  @Override
  public void showError(String toShow)
  {

  }

  @Override
  public String askUser()
  {
    return (JOptionPane.showInputDialog("What do you have to say?"));
  }
}
