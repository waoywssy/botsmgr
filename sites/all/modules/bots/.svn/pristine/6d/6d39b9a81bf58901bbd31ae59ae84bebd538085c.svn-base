package com.boryi.bots.client;

import java.util.ArrayList;

import com.boryi.bots.client.data.BotItem;
import com.boryi.bots.client.data.DataItem;
import com.boryi.bots.client.data.EventItem;
import com.boryi.bots.client.data.GroupItem;
import com.boryi.bots.client.data.JobItem;
import com.boryi.bots.client.data.LogItem;
import com.boryi.bots.client.data.QaItem;
import com.boryi.bots.client.data.RequestItem;
import com.boryi.bots.client.data.RowItem;
import com.boryi.bots.client.data.ServerItem;
import com.boryi.bots.client.data.TableList;
import com.boryi.bots.client.data.TableType;
import com.boryi.bots.client.data.TaskItem;
import com.boryi.bots.client.data.UserItem;
import com.boryi.bots.client.view.BotDialog;
import com.boryi.bots.client.view.DataDialog;
import com.boryi.bots.client.view.EditDialog;
import com.boryi.bots.client.view.EventDialog;
import com.boryi.bots.client.view.GroupDialog;
import com.boryi.bots.client.view.JobDialog;
import com.boryi.bots.client.view.LogDialog;
import com.boryi.bots.client.view.QaDialog;
import com.boryi.bots.client.view.RequestDialog;
import com.boryi.bots.client.view.ServerDialog;
import com.boryi.bots.client.view.TaskDialog;
import com.boryi.bots.client.view.UserDialog;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;


/**
 * Central panel on the right
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class CenterPanel extends Composite implements ClickHandler
{
  private final static int COLUMN_CHARS = 40;

  private ListBox sortByList = new ListBox();
  private RadioButton ascButton = new RadioButton("OrderBy", "ASC");
  private RadioButton descButton = new RadioButton("OrderBy", "DESC");

  private Button refreshButton = new Button("Refresh");

  private HTML selectAllLink = new HTML("<a href='javascript:;'>All</a>", true);
  private HTML selectNoneLink = new HTML("<a href='javascript:;'>None</a>",
      true);
  private HTML selectReverseLink = new HTML(
      "<a href='javascript:;'>Reverse</a>", true);

  private ListBox rowsPerPageList = new ListBox();

  private HTML[] pageButtons = new HTML[TableList.PAGES];

  private HTML firstButton = new HTML(
      "<a href='javascript:;'>&lt;&lt; first</a>", true);
  private HTML previousButton = new HTML(
      "<a href='javascript:;'>&lt; previous</a>", true);
  private HTML nextButton = new HTML("<a href='javascript:;'>next &gt;</a>",
      true);
  private HTML lastButton = new HTML(
      "<a href='javascript:;'>last &gt;&gt;</a>", true);

  private HorizontalPanel navBar = new HorizontalPanel();

  /**
   * Table list in the central panel to display list
   */
  private FlexTable table = new FlexTable();

  /**
   * Dialogs to show the details of table list
   */
  private EditDialog[] dialogs = new EditDialog[TableType.LOG.ordinal()];

  /**
   * Default the table type is BOT, and bots table is displayed
   */
  private TableType tableType = TableType.NONE;
  /**
   * Table data to be displayed
   */
  private TableList tableList;
  /**
   * Orders for each column (locally displayed), initialize to ascending
   */
  protected boolean[] columnOrders;


  /**
   * Constructs central panel
   */
  public CenterPanel()
  {
    VerticalPanel panel = new VerticalPanel();

    DockPanel optionPanel = new DockPanel();
    HorizontalPanel sortPanel = new HorizontalPanel();
    HorizontalPanel selectPanel = new HorizontalPanel();

    ascButton.setValue(true); // default ASC ordering
    sortPanel.setSpacing(4);
    sortPanel.add(new Label("Sort by"));
    sortPanel.add(sortByList);
    sortPanel.add(ascButton);
    sortPanel.add(descButton);
    sortPanel.add(new HTML("&nbsp;&nbsp;", true));
    sortPanel.add(refreshButton);

    selectPanel.setSpacing(4);
    selectPanel.add(new Label("Select: "));
    selectPanel.add(selectAllLink);
    selectPanel.add(selectNoneLink);
    selectPanel.add(selectReverseLink);

    // Create the 'navigation' bar at the upper-right.
    navBar.setSpacing(4);

    rowsPerPageList.addItem("25", "25");
    rowsPerPageList.addItem("50", "50");
    rowsPerPageList.addItem("75", "75");
    rowsPerPageList.addItem("100", "100");
    rowsPerPageList.addItem("150", "150");
    rowsPerPageList.addItem("200", "200");
    rowsPerPageList.setVisibleItemCount(1);
    rowsPerPageList.setSelectedIndex(1); // default 50 is chosen

    navBar.add(rowsPerPageList);
    navBar.add(new Label("rows per page"));
    navBar.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;", true));
    navBar.add(firstButton);
    navBar.add(previousButton);
    for (int i = 0; i < pageButtons.length; i++)
    {
      pageButtons[i] = new HTML();
      navBar.add(pageButtons[i]);
      pageButtons[i].addClickHandler(this);
    }
    navBar.add(nextButton);
    navBar.add(lastButton);

    optionPanel.add(sortPanel, DockPanel.NORTH);
    optionPanel.add(selectPanel, DockPanel.WEST);
    optionPanel.add(navBar, DockPanel.CENTER);

    optionPanel.setCellHorizontalAlignment(navBar, HorizontalPanel.ALIGN_RIGHT);
    optionPanel.setCellHorizontalAlignment(sortPanel,
        HorizontalPanel.ALIGN_LEFT);

    table.setCellSpacing(0);
    table.setCellPadding(5);
    table.setWidth("100%");

    rowsPerPageList.addClickHandler(this);
    firstButton.addClickHandler(this);
    previousButton.addClickHandler(this);
    nextButton.addClickHandler(this);
    lastButton.addClickHandler(this);

    refreshButton.addClickHandler(this);
    selectAllLink.addClickHandler(this);
    selectNoneLink.addClickHandler(this);
    selectReverseLink.addClickHandler(this);

    // Hook up events for table
    table.addClickHandler(this);

    optionPanel.addStyleName("centralPanelOption");
    table.addStyleName("centralPanelTable");
    panel.addStyleName("centralPanel");

    panel.add(optionPanel);
    panel.add(table);

    // Initialize the dialogs
    dialogs[TableType.USER.ordinal() - 1] = new UserDialog();
    dialogs[TableType.GROUP.ordinal() - 1] = new GroupDialog();
    dialogs[TableType.SERVER.ordinal() - 1] = new ServerDialog();
    dialogs[TableType.BOT.ordinal() - 1] = new BotDialog();
    dialogs[TableType.JOB.ordinal() - 1] = new JobDialog();
    dialogs[TableType.TASK.ordinal() - 1] = new TaskDialog();
    dialogs[TableType.RUN.ordinal() - 1] = null;
    dialogs[TableType.EVENT.ordinal() - 1] = new EventDialog(); // only show full details
    dialogs[TableType.QA.ordinal() - 1] = new QaDialog(); // only show full details
    dialogs[TableType.DATA.ordinal() - 1] = new DataDialog(); // only show full details
    dialogs[TableType.REQUEST.ordinal() - 1] = new RequestDialog();
    dialogs[TableType.LOG.ordinal() - 1] = new LogDialog(); // only show full details
    
    for (int i = 0; i < dialogs.length; i++)
    {
      if (dialogs[i] != null)
      {
        panel.add(dialogs[i]);
        dialogs[i].show();
        dialogs[i].hide();
      }
    }

    optionPanel.setWidth("100%");
    optionPanel.setCellWidth(sortPanel, "100%");
    optionPanel.setCellWidth(navBar, "100%");

    initWidget(panel);
    
    reset(Communicator.get().getBotList(), TableType.BOT);
  }


  /**
   * Click event on center panel - table Select or de-select the row
   */
  public void onClick(ClickEvent event)
  {
    Object sender = event.getSource();

    if (sender == refreshButton)
    {

    }
    else if (sender == selectAllLink)
    {
      ArrayList<RowItem> rows = tableList.getRows();
      for (int i = 0; i < rows.size(); i++)
      {
        rows.get(i).setSelected(true);
        CheckBox chkBox = (CheckBox) table.getWidget(i + 1, 0);
        chkBox.setValue(true);
      }
    }
    else if (sender == selectNoneLink)
    {
      ArrayList<RowItem> rows = tableList.getRows();
      for (int i = 0; i < rows.size(); i++)
      {
        rows.get(i).setSelected(false);
        CheckBox chkBox = (CheckBox) table.getWidget(i + 1, 0);
        chkBox.setValue(false);
      }
    }
    else if (sender == selectReverseLink)
    {
      ArrayList<RowItem> rows = tableList.getRows();
      for (int i = 0; i < rows.size(); i++)
      {
        boolean isSelected = !rows.get(i).isSelected();
        rows.get(i).setSelected(isSelected);
        CheckBox chkBox = (CheckBox) table.getWidget(i + 1, 0);
        chkBox.setValue(isSelected);
      }
    }
    else if (sender == rowsPerPageList)
    {

    }
    else if (sender == firstButton)
    {

    }
    else if (sender == previousButton)
    {

    }
    else if (sender == nextButton)
    {

    }
    else if (sender == lastButton)
    {

    }
    else if (sender == table)
    {
      Cell cell = table.getCellForEvent(event);

      if (cell != null)
      {
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getCellIndex();

        if (rowIndex > 0) // Table data
        {
          if (rowIndex - 1 < tableList.getRows().size())
          {
            if (columnIndex == 0) // Check box - selecting or de-selecting
            {
              boolean rowSelected = !tableList.getRows().get(rowIndex - 1)
                  .isSelected();
              tableList.getRows().get(rowIndex - 1).setSelected(rowSelected);
              CheckBox chkBox = (CheckBox) table.getWidget(rowIndex,
                  columnIndex);
              chkBox.setValue(rowSelected);
            }
            else if (columnIndex > 0) // Other columns to popup editing or
                                      // viewing page
            {
              if (dialogs[tableType.ordinal() - 1] != null)
              {
                dialogs[tableType.ordinal() - 1].show();
                dialogs[tableType.ordinal() - 1].center();
                dialogs[tableType.ordinal() - 1].viewItem(tableList.getRows().get(rowIndex - 1));
              }
            }
          }
          else
          // add a new row item
          {
            dialogs[tableType.ordinal() - 1].show();
            dialogs[tableType.ordinal() - 1].center();

            RowItem item = new BotItem();
            switch (tableType)
            {
              case USER:
                item = new UserItem();
                break;
              case GROUP:
                item = new GroupItem();
                break;
              case SERVER:
                item = new ServerItem();
                break;
              case BOT:
                item = new BotItem();
                break;
              case JOB:
                item = new JobItem();
                break;
              case TASK:
                item = new TaskItem();
                break;
              case EVENT:
                item = new EventItem();
                break;
              case QA:
                item = new QaItem();
                break;
              case DATA:
                item = new DataItem();
                break;
              case REQUEST:
                item = new RequestItem();
                break;
              case LOG:
                item = new LogItem();
                break;
            }
            dialogs[tableType.ordinal() - 1].addItem(item);
          }
        }
        else if (rowIndex == 0)// Table header to order the table by the column
                               // ASC or DESC
        {
          columnOrders[columnIndex] = !columnOrders[columnIndex];
          tableList.orderRowsByColumn(columnIndex, columnOrders[columnIndex]);
          update();
        }
      }
    }
    else
    {
      for (int i = 0; i < TableList.PAGES; i++)
      {
        if (sender == pageButtons[i])
        {

        }
      }
    }
  }


  /**
   * Reset the table according to user accessibility and commands
   * 
   * @param tableList
   *          data bundle to be displayed in the table
   * @param tableType
   *          type of the table to be displayed
   */
  public void reset(TableList tableList, TableType tableType)
  {
    this.tableList = tableList;

    // Initialize table headers and option panel if table type is changed
    if (this.tableType != tableType)
    {
      this.tableType = tableType;
      initOptionPanelAndTable();
    }

    // Set the data in the flex table, and reset pagination
    update();
  }


  /**
   * Initialize optional panel and table headers according to new type of table
   * list
   */
  public void initOptionPanelAndTable()
  {
    // Initialize option panel
    switch (tableType)
    {
      case USER:
      case GROUP:
      case BOT:
      case JOB:
      case TASK:
        sortByList.clear();
        sortByList.addItem("Id", "id");
        sortByList.addItem("Name", "name");
        sortByList.addItem("Enabled", "enabled");
        sortByList.addItem("Created", "created");
        sortByList.addItem("Updated", "updated");
        if (tableList.getSort() == "id")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "name")
        {
          sortByList.setSelectedIndex(1);
        }
        else if (tableList.getSort() == "enabled")
        {
          sortByList.setSelectedIndex(2);
        }
        else if (tableList.getSort() == "created")
        {
          sortByList.setSelectedIndex(3);
        }
        else if (tableList.getSort() == "updated")
        {
          sortByList.setSelectedIndex(4);
        }
        break;
      case SERVER:
        sortByList.clear();
        sortByList.addItem("Id", "id");
        sortByList.addItem("Name", "name");
        sortByList.addItem("Ip", "ip");
        sortByList.addItem("Enabled", "enabled");
        sortByList.addItem("CPU", "cpu");
        sortByList.addItem("Memory", "memory");
        sortByList.addItem("Disk", "disk");
        sortByList.addItem("Receiving", "receiving");
        sortByList.addItem("Sending", "sending");
        if (tableList.getSort() == "id")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "name")
        {
          sortByList.setSelectedIndex(1);
        }
        else if (tableList.getSort() == "ip")
        {
          sortByList.setSelectedIndex(2);
        }
        else if (tableList.getSort() == "enabled")
        {
          sortByList.setSelectedIndex(3);
        }
        else if (tableList.getSort() == "cpu")
        {
          sortByList.setSelectedIndex(4);
        }
        else if (tableList.getSort() == "memory")
        {
          sortByList.setSelectedIndex(5);
        }
        else if (tableList.getSort() == "disk")
        {
          sortByList.setSelectedIndex(6);
        }
        else if (tableList.getSort() == "receiving")
        {
          sortByList.setSelectedIndex(7);
        }
        else if (tableList.getSort() == "sending")
        {
          sortByList.setSelectedIndex(8);
        }
        break;
      case RUN:
        sortByList.clear();
        sortByList.addItem("Id", "id");
        sortByList.addItem("Start", "start");
        sortByList.addItem("Result", "result");
        if (tableList.getSort() == "id")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "start")
        {
          sortByList.setSelectedIndex(1);
        }
        else if (tableList.getSort() == "result")
        {
          sortByList.setSelectedIndex(2);
        }
        break;
      case EVENT:
        sortByList.clear();
        sortByList.addItem("Time", "time");
        sortByList.addItem("Severity", "severity");
        sortByList.addItem("Subject", "subject");
        if (tableList.getSort() == "time")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "severity")
        {
          sortByList.setSelectedIndex(1);
        }
        else if (tableList.getSort() == "subject")
        {
          sortByList.setSelectedIndex(2);
        }
        break;
      case QA:
        sortByList.clear();
        sortByList.addItem("Subject", "subject");
        sortByList.addItem("File", "file");
        sortByList.addItem("Created", "created");
        sortByList.addItem("Expire", "expire");
        if (tableList.getSort() == "subject")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "file")
        {
          sortByList.setSelectedIndex(1);
        }
        else if (tableList.getSort() == "created")
        {
          sortByList.setSelectedIndex(2);
        }
        else if (tableList.getSort() == "expire")
        {
          sortByList.setSelectedIndex(3);
        }
        break;
      case DATA:
        sortByList.clear();
        sortByList.addItem("Id", "id");
        sortByList.addItem("Time", "time");
        if (tableList.getSort() == "id")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "time")
        {
          sortByList.setSelectedIndex(1);
        }
        break;
      case REQUEST:
        sortByList.clear();
        sortByList.addItem("Id", "id");
        sortByList.addItem("User", "user");
        sortByList.addItem("Title", "title");
        sortByList.addItem("Created", "created");
        sortByList.addItem("Status", "status");
        if (tableList.getSort() == "id")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "user")
        {
          sortByList.setSelectedIndex(1);
        }
        else if (tableList.getSort() == "title")
        {
          sortByList.setSelectedIndex(2);
        }
        else if (tableList.getSort() == "created")
        {
          sortByList.setSelectedIndex(3);
        }
        else if (tableList.getSort() == "status")
        {
          sortByList.setSelectedIndex(4);
        }
        break;
      case LOG:
        sortByList.clear();
        sortByList.addItem("Id", "id");
        sortByList.addItem("Subject", "subject");
        if (tableList.getSort() == "id")
        {
          sortByList.setSelectedIndex(0);
        }
        else if (tableList.getSort() == "subject")
        {
          sortByList.setSelectedIndex(1);
        }
        break;
    }

    // Set order type
    if (!tableList.isSortAsc())
    {
      descButton.setValue(true);
      ascButton.setValue(false);
    }
    else
    {
      ascButton.setValue(true);
      descButton.setValue(false);
    }

    ArrayList<String> headers = tableList.getHeaders();
    int rowsPerPage = tableList.getRowsPerPage();

    // Clear table
    while (table.getRowCount() > 0)
    {
      table.removeRow(0);
    }

    // Set table headers and initialize column orders
    columnOrders = new boolean[tableList.getHeaders().size()];
    for (int i = 0; i < headers.size(); i++)
    {
      table.setText(0, i, headers.get(i));
      columnOrders[i] = true;
    }
    table.getRowFormatter().setStyleName(0, "centralPanelTableHeader");

    // Initialize the rest of the rows.
    for (int i = 0; i < rowsPerPage; ++i)
    {
      for (int j = 0; j < headers.size(); j++)
      {
        table.setText(i + 1, j, "");
        table.getCellFormatter().setWordWrap(i + 1, j, false);
      }
    }

    // Set rowsPerPage list box
    int index = 0;
    switch (rowsPerPage)
    {
      case 50:
        index = 1;
        break;
      case 75:
        index = 2;
        break;
      case 100:
        index = 3;
        break;
      case 150:
        index = 4;
        break;
      case 200:
        index = 5;
        break;
    }
    rowsPerPageList.setSelectedIndex(index);
  }


  /**
   * Update the table list and pagination links
   */
  private void update()
  {
    // Set page links - first, previous, next, last buttons
    firstButton.setVisible(tableList.isFirstEnabled());
    previousButton.setVisible(tableList.isPreviousEnabled());
    nextButton.setVisible(tableList.isNextEnabled());
    lastButton.setVisible(tableList.isLastEnabled());

    // Set page links - 5 pages
    if (tableList.getTotalPages() == 1)
    {
      for (int i = 0; i < pageButtons.length; i++)
      {
        pageButtons[i].setVisible(false);
      }
    }
    else
    {
      int pages = tableList.getEndPage() - tableList.getStartPage() + 1;
      for (int i = 0; i < pages; i++)
      {
        int page = i + tableList.getStartPage();
        if (page == tableList.getCurrentPage())
        {
          pageButtons[i].setHTML(Integer.toString(page));
        }
        else
        {
          pageButtons[i].setHTML("<a href='javascript:;'>" + page + "</a>");
        }
        pageButtons[i].setVisible(true);
      }
      for (int i = pages; i < pageButtons.length; i++)
      {
        pageButtons[i].setVisible(false);
      }
    }

    // Set table values
    int columns = tableList.getHeaders().size();
    ArrayList<RowItem> rows = tableList.getRows();
    for (int i = 0; i < rows.size(); i++)
    {
      // Set check box
      CheckBox selected = new CheckBox();
      selected.setValue(Boolean.parseBoolean(rows.get(i).getColumn(0)));
      table.setWidget(i + 1, 0, selected);

      // Set column values
      for (int j = 1; j < columns; j++)
      {
        String text = rows.get(i).getColumn(j);
        if (text.length() > COLUMN_CHARS)
        {
          text = text.substring(0, COLUMN_CHARS - 4) + "...";
        }
        table.setText(i + 1, j, text);
      }

      // Set odd and even row style
      if ((i + 1) % 2 == 1)
      {
        table.getRowFormatter().setStyleName(i + 1, "centralPanelTableRowOdd");
      }
      else
      {
        table.getRowFormatter().setStyleName(i + 1, "centralPanelTableRowEven");
      }
    }

    // Set rest of rows empty in a page
    if (rows.size() < tableList.getRowsPerPage())
    {
      for (int i = rows.size(); i < tableList.getRowsPerPage(); i++)
      {
        for (int j = 0; j < columns; j++)
        {
          table.setHTML(i + 1, j, "&nbsp;");
        }
        table.getRowFormatter().setStyleName(i + 1, "centralPanelTableRowOdd");
      }
    }
  }
}
