package com.boryi.bots.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * About Boryi Bots Management System
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class AboutDialog extends DialogBox
{
  /**
   * Constructs about dialog
   */
  public AboutDialog()
  {
    // Use this opportunity to set the dialog's caption.
    setText("About the Boryi Bots Management System");

    // Create a VerticalPanel to contain the 'about' label and the 'OK' button.
    VerticalPanel outer = new VerticalPanel();

    // Create the 'about' text and set a style name so we can style it with CSS.

    HTML text = new HTML(
        "The Boryi Bots Management System was developed by Boryi "
            + "Network Information Inc.  Use the system to manage the servers and the "
            + "web bots or webcrawlers, schedule the crawling, monitor the QA results, "
            + "download data, and request services from us.  Thank you for your "
            + "interest in using the system.");

    text.setStyleName("aboutText");
    outer.add(text);

    // Create the 'OK' button, along with a handler that hides the dialog
    // when the button is clicked.
    Button ok = new Button("OK", new ClickHandler() {
      public void onClick(ClickEvent event)
      {
        hide();
      }
    });

    outer.add(ok);
    ok.setStyleName("aboutOk");

    setWidget(outer);
  }


  /**
   * Key down to close the about dialog
   */
  @Override
  public boolean onKeyDownPreview(char key, int modifiers)
  {
    // Use the popup's key preview hooks to close the dialog when either
    // enter or escape is pressed.
    switch (key)
    {
      case KeyCodes.KEY_ENTER:
      case KeyCodes.KEY_ESCAPE:
        hide();
        break;
    }

    return true;
  }
}
