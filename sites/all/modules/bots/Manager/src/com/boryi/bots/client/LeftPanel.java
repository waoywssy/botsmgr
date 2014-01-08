package com.boryi.bots.client;

import com.boryi.bots.client.data.UserPrivilege;
import com.boryi.bots.client.data.TableType;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;


/**
 * Left panel menu items
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class LeftPanel extends Composite
{
  /**
   * An image bundle specifying the images for this Widget and aggragating
   * images needed in child widgets.
   */
  public interface Images
  {
    AbstractImagePrototype accounts();


    AbstractImagePrototype servers();


    AbstractImagePrototype bots();


    AbstractImagePrototype data();


    AbstractImagePrototype requests();


    AbstractImagePrototype logs();
  }

  private Images images;

  private Command cmdUsers;
  private Command cmdGroups;
  private Command cmdServers;
  private Command cmdBots;
  private Command cmdJobs;
  private Command cmdTasks;
  private Command cmdRuns;
  private Command cmdEvents;
  private Command cmdQa;
  private Command cmdData;
  private Command cmdRequests;
  private Command cmdLogs;

  private MenuBar menu = new MenuBar(true);
  private MenuBar menuAccounts = new MenuBar(true);
  private MenuBar menuBots = new MenuBar(true);
  private MenuItem itemUsers;
  private MenuItem itemGroups;
  private MenuItem itemBots;
  private MenuItem itemJobs;
  private MenuItem itemTasks;
  private MenuItem itemRuns;
  private MenuItem itemEvents;
  private MenuItem itemQa;


  /**
   * Constructs a new shortcuts widget using the specified images.
   * 
   * @param images
   *          a bundle that provides the images for this widget
   */
  public LeftPanel(Images images)
  {
    this.images = images;
    reset();

    initWidget(menu);
  }


  public void reset()
  {
    cmdUsers = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getUserList(), TableType.USER);
      }
    };
    cmdGroups = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getGroupList(), TableType.GROUP);
      }
    };
    cmdServers = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getServerList(), TableType.SERVER);
      }
    };
    cmdBots = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getBotList(), TableType.BOT);
      }
    };
    cmdJobs = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getJobList(), TableType.JOB);
      }
    };
    cmdTasks = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getTaskList(), TableType.TASK);
      }
    };
    cmdRuns = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getRunList(), TableType.RUN);
      }
    };
    cmdEvents = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getEventList(), TableType.EVENT);
      }
    };
    cmdQa = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getQaList(), TableType.QA);
      }
    };
    cmdData = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getDataList(), TableType.DATA);
      }
    };
    cmdRequests = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getRequestList(), TableType.REQUEST);
      }
    };
    cmdLogs = new Command() {
      public void execute()
      {
        Manager.get().getMainPanel().getCenterPanel().reset(Communicator.get().getLogList(), TableType.LOG);
      }
    };

    // Only administrator can manage accounts
    if (Communicator.get().getUserPrivilege() == UserPrivilege.ADMIN)
    {
      itemUsers = new MenuItem("Users", cmdUsers);
      itemGroups = new MenuItem("Groups", cmdGroups);
      menuAccounts.addItem(itemUsers);
      menuAccounts.addItem(itemGroups);

      menu.addItem(createHeaderHTML(images.accounts(), "Accounts"), true,
          menuAccounts);
    }

    // Only administrator and developer can manage servers
    if (Communicator.get().getUserPrivilege() == UserPrivilege.ADMIN
        || Communicator.get().getUserPrivilege() == UserPrivilege.DEVELOPER)
    {
      menu.addItem(createHeaderHTML(images.servers(), "Servers"), true,
          cmdServers);
    }

    itemBots = new MenuItem("Bots", cmdBots);
    itemJobs = new MenuItem("Jobs", cmdJobs);
    itemTasks = new MenuItem("Tasks", cmdTasks);
    itemRuns = new MenuItem("Runs", cmdRuns);
    itemEvents = new MenuItem("Events", cmdEvents);
    itemQa = new MenuItem("QA", cmdQa);
    menuBots.addItem(itemBots);
    menuBots.addItem(itemJobs);
    menuBots.addItem(itemTasks);
    menuBots.addItem(itemRuns);
    menuBots.addItem(itemEvents);
    menuBots.addItem(itemQa);
    menu.addItem(createHeaderHTML(images.bots(), "Bots"), true, menuBots);

    menu.addItem(createHeaderHTML(images.data(), "Data"), true, cmdData);
    
    // Except guest, all can manage requests
    if (Communicator.get().getUserPrivilege() != UserPrivilege.USER)
    {
      menu.addItem(createHeaderHTML(images.requests(), "Requests"), true,
          cmdRequests);
    }
    
    // Only administrator can manage logs
    if (Communicator.get().getUserPrivilege() == UserPrivilege.ADMIN)
    {
      menu.addItem(createHeaderHTML(images.logs(), "Logs"), true, cmdLogs);
    }

    menu.addStyleName("leftPanel");

    menu.setAutoOpen(true);
  }


  /**
   * Creates an HTML fragment that places an image & caption together, for use
   * in a group header.
   * 
   * @param imageProto
   *          an image prototype for an image
   * @param caption
   *          the group caption
   * @return the header HTML fragment
   */
  private String createHeaderHTML(AbstractImagePrototype imageProto,
      String caption)
  {
    String captionHTML = "<table class='leftPanelTopMenuItem'><tr><td>"
        + imageProto.getHTML() + "</td><td>&nbsp;&nbsp;</td><td><b>" + caption
        + "</b></td></tr></table>";
    return captionHTML;
  }
}
