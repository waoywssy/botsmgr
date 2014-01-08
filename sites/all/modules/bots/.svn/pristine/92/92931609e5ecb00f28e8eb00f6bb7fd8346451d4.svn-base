package com.boryi.bots.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Login dialog to login system using user name and password. Inputs are
 * verified before sending to the server.
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class LoginPanel extends Composite implements ClickHandler,
    KeyPressHandler
{
  private final static String USER_ERROR = "Illegal character in user name.";
  private final static String PASSWORD_ERROR = "Illegal character in password.";
  private final static String USER_PASSWORD_ERROR = "Illegal character in user name and password.";
  private final static String LOGING = "Verifying ...";
  private final static String LOGIN_ERROR = "Incorrect username or password, please try again.";

  private final SimplePanel panel = new SimplePanel();
  private final DialogBox dialogBox = new DialogBox();

  private final VerticalPanel verticalPanel = new VerticalPanel();

  private final HorizontalPanel userPanel = new HorizontalPanel();
  private final HorizontalPanel pwdPanel = new HorizontalPanel();
  private final HorizontalPanel btnPanel = new HorizontalPanel();

  private final Label usernameLabel = new Label("User");
  private final Label passwordLabel = new Label("Password");
  private final Label messageLabel = new Label(LOGING);

  private final TextBox usernameTextBox = new TextBox();
  private final PasswordTextBox passwordTextBox = new PasswordTextBox();

  private final Button resetButton = new Button("Reset");
  private final Button loginButton = new Button("Login");


  /**
   * Constructs login panel with event handlers
   */
  public LoginPanel()
  {
    // Set the dialog box's caption.
    dialogBox.setText("Log in to Boryi Bots Management System");

    usernameTextBox.setFocus(true);
    messageLabel.setVisible(false);

    // Set the style dependent names for panels, labels, text boxes, buttons,
    // and message label
    panel.addStyleName("loginPanel");

    dialogBox.addStyleName("loginDialog");

    verticalPanel.addStyleName("loginVerticalPanel");

    userPanel.addStyleName("loginHorizontalPanel");
    pwdPanel.addStyleName("loginHorizontalPanel");
    btnPanel.addStyleName("loginHorizontalPanel");

    usernameLabel.addStyleName("loginLabel");
    passwordLabel.addStyleName("loginLabel");

    usernameTextBox.addStyleName("loginTextBox");
    passwordTextBox.addStyleName("loginTextBox");

    resetButton.addStyleName("loginButton");
    resetButton.getElement().setId("loginResetButton");
    loginButton.addStyleName("loginButton");

    messageLabel.getElement().setId("loginMessageLabel");

    // Set the length of TextBox
    usernameTextBox.setMaxLength(40);
    passwordTextBox.setMaxLength(40);

    // Organize the widgets
    userPanel.add(usernameLabel);
    userPanel.add(usernameTextBox);

    pwdPanel.add(passwordLabel);
    pwdPanel.add(passwordTextBox);

    btnPanel.add(resetButton);
    btnPanel.add(loginButton);

    verticalPanel.add(userPanel);
    verticalPanel.add(pwdPanel);
    verticalPanel.add(btnPanel);
    verticalPanel.add(messageLabel);

    dialogBox.add(verticalPanel);

    panel.add(dialogBox);

    // Add mouse click event handlers to buttons
    resetButton.addClickHandler(this);
    loginButton.addClickHandler(this);

    // Add key press event handlers to text boxes
    usernameTextBox.addKeyPressHandler(this);
    passwordTextBox.addKeyPressHandler(this);

    initWidget(panel);
    setVisible(true);
  }


  /**
   * Add mouse click event handlers to login dialog
   * 
   * @param event
   *          Mouse click event on login dialog
   */
  public void onClick(ClickEvent event)
  {
    Object sender = event.getSource();

    if (sender == resetButton)
    {
      reset();
    }
    else if (sender == loginButton)
    {
      login();
    }
  }


  /**
   * Add key press event handlers to login dialog
   * 
   * @param event
   *          Key press event on login dialog
   */
  public void onKeyPress(KeyPressEvent event)
  {
    if (event.getSource() instanceof Widget)
    {
      Widget sender = (Widget) event.getSource();

      if (event.getCharCode() == KeyCodes.KEY_ENTER
          && (sender == usernameTextBox || sender == passwordTextBox))
      {
        login();
      }
    }
  }


  /**
   * Hide or show the dialog
   */
  @Override
  public void setVisible(boolean visible)
  {
    super.setVisible(visible);
    if (visible)
    {
      dialogBox.show();
      dialogBox.center();
    }
    else
    {
      dialogBox.hide();
    }
  }


  /**
   * Reset the login user name and password
   */
  public void reset()
  {
    messageLabel.setVisible(false);
    usernameTextBox.setText("");
    passwordTextBox.setText("");
  }


  /**
   * When login button was clicked, or enter key was pressed. Verify the inputs.
   * Retrieve the login session key from the server. Close login panel, show
   * next panel if the user successfully logged in.
   */
  private void login()
  {
    messageLabel.setText(LOGING);
    messageLabel.getElement().setId("loginMessageLabel");
    messageLabel.setVisible(true);
    
    String username = usernameTextBox.getText().trim();
    String password = passwordTextBox.getText().trim();

    if (validateInputs(username, password))
    {
      if (Communicator.get().login(username, password))
      {
        Manager.get().hideLoginPanel();
        Manager.get().showMainPanel();
      }
      else
      {
        messageLabel.setText(LOGIN_ERROR);
        messageLabel.getElement().setId("loginErrorMessageLabel");
      }
    }
  }


  /**
   * Use regex to verify the inputs: user name and password. Illegal characters
   * are not allowed. User name can not be empty
   * 
   * @param username user name to be logged in
   * @param password password of the user
   * @return Whether the inputs have been verified
   */
  private boolean validateInputs(String username, String password)
  {
    int result = 0;
    if (!username.matches("^[0-9a-zA-Z\\.]{1,40}$"))
    {
      result = 1;
    }
    if (!password.matches("^[0-9a-zA-Z@#$%&*_\\-\\.]{0,40}$"))
    {
      result += 2;
    }

    if (result > 0)
    {
      usernameTextBox.setFocus(true);
      switch (result)
      {
        case 1:
          messageLabel.setText(USER_ERROR);
          break;
        case 2:
          messageLabel.setText(PASSWORD_ERROR);
          passwordTextBox.setFocus(true);
          break;
        case 3:
          messageLabel.setText(USER_PASSWORD_ERROR);
          break;
      }
      messageLabel.getElement().setId("loginErrorMessageLabel");
      return false;
    }
    return true;
  }
}
