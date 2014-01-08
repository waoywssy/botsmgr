package com.boryi.bots.client.view;

import com.boryi.bots.client.data.RowItem;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Editing or viewing dialog
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public abstract class EditDialog extends DialogBox implements ClickHandler
{
  protected VerticalPanel outer = new VerticalPanel();
  protected FlexTable inner = new FlexTable();

  protected Button editButton = new Button("Edit");
  protected Button restoreButton = new Button("Restore");
  protected Button submitButton = new Button("Submit");
  protected Button cancelButton = new Button("Cancel");


  /**
   * Constructs an editing or viewing dialog
   */
  public EditDialog()
  {
    // Set dialog caption
    setText("Boryi Bot Management System - View and Edit Dialog");

    inner.setStyleName("editDialogInner");

    HorizontalPanel bottomPanel = new HorizontalPanel();
    bottomPanel.add(cancelButton);
    bottomPanel.add(editButton);
    bottomPanel.add(restoreButton);
    bottomPanel.add(submitButton);

    restoreButton.setEnabled(false);
    submitButton.setEnabled(false);

    cancelButton.setStyleName("editDialogButton");
    editButton.setStyleName("editDialogButton");
    restoreButton.setStyleName("editDialogButton");
    submitButton.setStyleName("editDialogButton");
    bottomPanel.setStyleName("editDialogBottomPanel");

    cancelButton.addClickHandler(this);
    editButton.addClickHandler(this);
    restoreButton.addClickHandler(this);
    submitButton.addClickHandler(this);

    outer.add(inner);
    outer.add(bottomPanel);

    setWidget(outer);
    hide();
  }


  /**
   * Mouse click on dialog
   */
  public void onClick(ClickEvent event)
  {
    Object sender = event.getSource();
    if (sender == cancelButton)
    {
      hide();
    }
    else if (sender == editButton)
    {
      editItem();
    }
    else if (sender == restoreButton)
    {
      restoreItem();
    }
    else if (sender == submitButton)
    {
      if (verify())
      {
        // TODO send item and update item
        sendItem();
        hide();
      }
    }
  }


  @Override
  public boolean onKeyDownPreview(char key, int modifiers)
  {
    // Use the popup's key preview hooks to close the dialog when either
    // enter or escape is pressed.
    switch (key)
    {
      case KeyCodes.KEY_ESCAPE:
        hide();
        break;
    }

    return true;
  }


  /**
   * Add a new row item
   * 
   * @param item
   *          row item to be added to the table list
   */
  public abstract void addItem(RowItem item);


  /**
   * View a row item
   * 
   * @param item
   *          row item to be viewed
   */
  public abstract void viewItem(RowItem item);


  /**
   * Verify the inputs
   * 
   * @return whether the inputs have been verified
   */
  protected abstract boolean verify();

  
  /**
   *  Restore the item value in editing mode
   */
  protected abstract void restoreItem();

  
  /**
   * Restore the item value and go back to viewing mode
   */
  protected abstract void reset();
  
  
  /**
   * Change the dialog to edit mode, in order to edit item
   */
  protected abstract void editItem();
  
  
  /**
   * Send item to server and update the local value
   */
  protected abstract void sendItem();
}
