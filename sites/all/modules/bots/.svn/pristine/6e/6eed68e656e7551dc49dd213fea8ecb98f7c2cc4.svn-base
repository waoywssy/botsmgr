package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for user groups
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public class GroupList extends TableList
{
  /**
   * Constructs an instance of group list and initialize headers.
   * Click to edit a group.
   * Group description is displayed by popup panel.
   */
  public GroupList()
  {
    headers = new ArrayList<String>(7);
    headers.add("Select"); // whether the user group is selected
    headers.add("Id"); // id of the group
    headers.add("Category"); // category the group belongs to, such as admin, developer, etc.
    headers.add("Name"); // name of the group
    headers.add("Enabled"); // whether the group is enabled
    headers.add("Created"); // When the group was created
    headers.add("Updated"); // when the group was last updated
    headers.add("Description"); // when the group was last updated
    
    sort = "id"; // sort by group id
    // name: sort by name 
    // enabled: sort by enabled
    // created: sort by created
    // updated:  sort by last updated
  }
}
