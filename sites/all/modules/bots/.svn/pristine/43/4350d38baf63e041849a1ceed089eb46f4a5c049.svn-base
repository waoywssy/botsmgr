package com.boryi.bots.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Top panel include logo, welcome message, about and logout links
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class TopPanel extends Composite implements ClickHandler
{
  /**
   * An image bundle for this widgets images.
   */
  public interface Images extends ImageBundle
  {
    AbstractImagePrototype logo();
  }

  private HTML welcome = new HTML("<b>Welcome back, "
      + Communicator.get().getLoginUser() + "!</b>");
  private HTML logOutLink = new HTML("<a href='javascript:;'>Log Out</a>");
  private HTML aboutLink = new HTML("<a href='javascript:;'>About</a>");


  /**
   * Constructs top panel
   * 
   * @param images
   *          Image bundle includes the logo
   */
  public TopPanel(Images images)
  {
    final Image logo = images.logo().createImage();

    HorizontalPanel outer = new HorizontalPanel();
    VerticalPanel inner = new VerticalPanel();

    HorizontalPanel links = new HorizontalPanel();
    links.setSpacing(4);
    links.add(logOutLink);
    links.add(aboutLink);

    inner.add(welcome);
    inner.add(links);

    outer.add(logo);
    outer.add(inner);

    logOutLink.addClickHandler(this);
    aboutLink.addClickHandler(this);

    logo.addStyleName("topPanelLogo");
    links.setStyleName("topPanelLinks");
    inner.addStyleName("topPanelInfo");
    outer.addStyleName("topPanel");

    initWidget(outer);
  }


  /**
   * Reset login user name of the welcome message
   */
  public void reset()
  {
    welcome.setHTML("<b>Welcome back, " + Communicator.get().getLoginUser()
        + "!</b>");
  }


  /**
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
   */
  public void onClick(ClickEvent event)
  {
    Object sender = event.getSource();

    if (sender == logOutLink)
    {
      Cookies.removeCookie(Communicator.COOKIE_LOGIN_SESSION);
      Cookies.removeCookie(Communicator.COOKIE_LOGIN_USER);

      Manager.get().hideMainPanel();
      Manager.get().showLoginPanel();
    }
    else if (sender == aboutLink)
    {
      // When the 'About' item is selected, show the AboutDialog.
      // Note that showing a dialog box does not block -- execution continues
      // normally, and the dialog fires an event when it is closed.
      AboutDialog dlg = new AboutDialog();
      dlg.show();
      dlg.center();
    }
  }
}
