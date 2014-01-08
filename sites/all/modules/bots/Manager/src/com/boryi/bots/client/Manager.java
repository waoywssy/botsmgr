package com.boryi.bots.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class Manager implements EntryPoint
{
  private static Manager singleton;

  /**
   * Logged in panel - manage the system
   */
  private MainPanel mainPanel;
  /*
   * Login panel - user name and password
   */
  private LoginPanel loginPanel;


  /**
   * Gets the singleton Manager instance
   * 
   * @return The singleton Manager instance
   */
  public static Manager get()
  {
    return singleton;
  }


  /**
   * This is the entry point method.
   */
  public void onModuleLoad()
  {
    singleton = this;

    if (Communicator.get().verifyLogin())
    {
      showMainPanel();
    }
    else
    {
      showLoginPanel();
    }
  }


  /**
   * Show login panel. Create a new instance and add to root panel only once, or
   * set it visible.
   */
  public void showLoginPanel()
  {
    if (loginPanel == null)
    {
      loginPanel = new LoginPanel();
      RootPanel.get().add(loginPanel);
    }
    else
    {
      loginPanel.reset();
      loginPanel.setVisible(true);
    }
  }


  /**
   * Hide login panel by setting it invisible
   */
  public void hideLoginPanel()
  {
    if (loginPanel != null)
    {
      loginPanel.setVisible(false);
    }
  }


  /**
   * Show main panel when logged in. Create a new instance and add to root panel
   * once, or set it visible.
   */
  public void showMainPanel()
  {
    if (mainPanel == null)
    {
      mainPanel = new MainPanel();
      RootPanel.get().add(mainPanel);
    }
    else
    {
      mainPanel.reset();
      mainPanel.setVisible(true);
    }
  }


  /**
   * Hide main panel by setting it invisible
   */
  public void hideMainPanel()
  {
    if (mainPanel != null)
    {
      mainPanel.setVisible(false);
    }
  }
  
  
  /**
   * Get the main panel
   * 
   * @return the main panel
   */
  public MainPanel getMainPanel()
  {
    return mainPanel;
  }
}
