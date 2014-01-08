package com.boryi.bots.client.view;

import com.boryi.bots.client.data.BotItem;
import com.boryi.bots.client.data.RowItem;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;


/**
 * Bot dialog to view or edit bot entry
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class BotDialog extends EditDialog
{
  private Label idLabel = new Label("Bot Id:");
  private Label idTextLabel = new Label();

  private Label nameLabel = new Label("Bot Name:");
  private TextBox nameText = new TextBox();

  private Label versionLabel = new Label("Version:");
  private TextBox versionText = new TextBox();

  private Label descriptionLabel = new Label("Description:");
  private TextArea descriptionText = new TextArea();

  private CheckBox enabledCheckBox = new CheckBox("Enabled");

  private Label botFileLabel = new Label("Bot File:");
  private Label botFileTextLabel = new Label();
  private CheckBox uploadBotFile = new CheckBox("Upload a new bot file?");

  private AsyncFileUpload fileUpload = new AsyncFileUpload("", "botFilename",
      30, 3000);

  private BotItem item;


  /**
   * Constructs bot dialog for editing or viewing
   */
  public BotDialog()
  {
    botFileLabel.setWordWrap(true);

    nameText.setMaxLength(128);
    versionText.setMaxLength(20);
    descriptionText.setCharacterWidth(70);
    descriptionText.setVisibleLines(4);

    inner.setCellPadding(5);
    inner.setCellSpacing(0);

    inner.setWidget(0, 0, idLabel);
    inner.setWidget(0, 1, idTextLabel);

    inner.setWidget(0, 2, enabledCheckBox);
    inner.getFlexCellFormatter().setColSpan(0, 2, 2);

    inner.setWidget(1, 0, nameLabel);
    inner.setWidget(1, 1, nameText);

    inner.setWidget(1, 2, versionLabel);
    inner.setWidget(1, 3, versionText);

    inner.setWidget(2, 0, descriptionLabel);
    inner.getFlexCellFormatter().setColSpan(2, 0, 4);

    inner.setWidget(3, 0, descriptionText);
    inner.getFlexCellFormatter().setColSpan(3, 0, 4);

    inner.setWidget(4, 0, botFileLabel);
    inner.setWidget(4, 1, botFileTextLabel);
    inner.getFlexCellFormatter().setColSpan(4, 1, 3);

    inner.setWidget(5, 0, uploadBotFile);
    inner.getFlexCellFormatter().setColSpan(5, 0, 4);

    inner.setWidget(6, 0, fileUpload);
    inner.getFlexCellFormatter().setColSpan(6, 0, 4);

    uploadBotFile.addClickHandler(this);
  }


  @Override
  protected boolean verify()
  {
    if (nameText.getText().trim().matches("^[0-9a-zA-Z_\\.\\-\\s]{1,128}$") && versionText
        .getText().trim().matches("^[0-9a-zA-Z_\\.\\-\\s]{1,128}$"))
    {
      return true;
    }
    else
    {
      Window.alert("Bot name or version is invalid.");
      return false;
    }
  }


  @Override
  protected void restoreItem()
  {
    if (item != null)
    {
      idTextLabel.setText(Integer.toString(item.getId()));
      enabledCheckBox.setValue(item.isEnabled());
      nameText.setText(item.getName());
      versionText.setText(item.getVersion());
      descriptionText.setText(item.getDescription());
      botFileTextLabel.setText(item.getFilename());
    }
  }


  @Override
  protected void reset()
  {
    restoreItem();

    nameText.setEnabled(false);
    versionText.setEnabled(false);
    enabledCheckBox.setEnabled(false);
    descriptionText.setEnabled(false);

    botFileLabel.setVisible(true);
    botFileTextLabel.setVisible(true);

    uploadBotFile.setValue(false);
    uploadBotFile.setVisible(false);
    fileUpload.setVisible(false);

    editButton.setEnabled(true);
    restoreButton.setEnabled(false);
    submitButton.setEnabled(false);
  }


  @Override
  public void addItem(RowItem item)
  {
    this.item = (BotItem) item;
    restoreItem();

    nameText.setEnabled(true);
    versionText.setEnabled(true);
    enabledCheckBox.setEnabled(true);
    descriptionText.setEnabled(true);

    botFileLabel.setVisible(false);
    botFileTextLabel.setVisible(false);

    uploadBotFile.setVisible(false);
    fileUpload.setVisible(true);

    editButton.setEnabled(false);
    restoreButton.setEnabled(false);
    submitButton.setEnabled(true);
  }


  @Override
  public void viewItem(RowItem item)
  {
    this.item = (BotItem) item;
    reset();
  }


  @Override
  protected void editItem()
  {
    nameText.setEnabled(true);
    versionText.setEnabled(true);
    enabledCheckBox.setEnabled(true);
    descriptionText.setEnabled(true);

    botFileLabel.setVisible(true);
    botFileTextLabel.setVisible(true);

    uploadBotFile.setVisible(true);
    uploadBotFile.setValue(false);
    fileUpload.setVisible(false);

    editButton.setEnabled(false);
    restoreButton.setEnabled(true);
    submitButton.setEnabled(true);
  }


  @Override
  protected void sendItem()
  {
    item.setEnabled(enabledCheckBox.getValue());
    item.setName(nameText.getText().trim());
    item.setVersion(versionText.getText().trim());
    item.setDescription(descriptionText.getText().trim());
    item.setFilename(fileUpload.getServerFilename());
  }


  @Override
  public void onClick(ClickEvent event)
  {
    super.onClick(event);

    Object sender = event.getSource();
    if (sender == uploadBotFile)
    {
      fileUpload.setVisible(uploadBotFile.getValue());
    }
  }
}
