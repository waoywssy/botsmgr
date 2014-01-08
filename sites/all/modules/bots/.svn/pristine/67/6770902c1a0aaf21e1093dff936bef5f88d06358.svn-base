package com.boryi.bots.client;

import com.boryi.bots.client.data.TableType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;


/**
 * Main panel to manage the Boryi Bots
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class MainPanel extends Composite
{
  /**
   * Instantiate an application-level image bundle. This object will provide
   * programmatic access to all the images needed by widgets.
   */
  private static final Images images = GWT.create(Images.class);

  /**
   * An aggragate image bundle that pulls together all the images for this
   * application into a single bundle.
   */
  public interface Images extends LeftPanel.Images, TopPanel.Images
  {
  }

  private DockPanel panel = new DockPanel();
  private TopPanel topPanel = new TopPanel(images);
  private CenterPanel centerPanel = new CenterPanel();
  private LeftPanel leftPanel = new LeftPanel(images);


  /**
   * Constructor to initialize the Management Panel
   */
  public MainPanel()
  {
    panel.addStyleName("mainPanel");

    panel.add(topPanel, DockPanel.NORTH);
    panel.add(leftPanel, DockPanel.WEST);
    panel.add(centerPanel, DockPanel.CENTER);

    panel.setSpacing(4);

    initWidget(panel);
  }


  /**
   * Reset the components of the main panel: welcome message, left panel menu
   * accessibility for different user, right panel bots accessibility for
   * different user.
   */
  public void reset()
  {
    topPanel.reset();
    leftPanel.reset();
    centerPanel.reset(Communicator.get().getBotList(), TableType.BOT);
  }


  /**
   * Get the center panel
   * 
   * @return the center panel
   */
  public CenterPanel getCenterPanel()
  {
    return centerPanel;
  }
}
