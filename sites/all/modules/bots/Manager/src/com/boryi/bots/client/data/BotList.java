package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for bots
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class BotList extends TableList
{
  /**
   * Constructs an instance of bot list. Click to edit a bot. Bot description is
   * displayed by popup panel.
   */
  public BotList()
  {
    headers = new ArrayList<String>(8);
    headers.add("Select"); // whether the bot is selected to view its jobs, etc.
    headers.add("Id"); // id of the bot
    headers.add("Name"); // name of the bot
    headers.add("Version"); // version of the bot jar file
    headers.add("Enabled"); // whether the bot is enabled: Y / N
    headers.add("Created"); // when was the bot created
    headers.add("Updated"); // when was the bot last updated
    headers.add("Description"); // Brief description

    // see: CenterPanel.java initOptionPanelAndTable()
    sort = "id"; // sort by bot id
    // name: sort by name (, bot id)
    // enabled: sort by enabled, bot id
    // created: sort by created, bot id
    // updated: sort by updated, bot id
  }
}
