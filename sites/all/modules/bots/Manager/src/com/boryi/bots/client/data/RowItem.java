package com.boryi.bots.client.data;

/**
 * Data bundle for the row of table
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public abstract class RowItem
{
  protected boolean selected = false;
  protected int id = 0;
  
  /**
   * Whether the row is selected
   * 
   * @return whether the row is selected
   */
  public boolean isSelected()
  {
    return selected;
  }


  /**
   * Get the id of the row data
   * 
   * @return the id of the row data
   */
  public int getId()
  {
    return id;
  }


  /**
   * Set the id of the row data
   * 
   * @param id
   *          the id of the row data
   */
  public void setId(int id)
  {
    this.id = id;
  }
  
  
  /**
   * Set whether the row is selected
   * 
   * @param selected
   *          whether the row is selected
   */
  public void setSelected(boolean selected)
  {
    this.selected = selected;
  }


  /**
   * Get the column of given index in string representation
   * 
   * @param index
   *          the index of the column
   * @return the string representation of the column
   */
  public abstract String getColumn(int index);


  /**
   * Set the column of given index in string representation
   * For JSON setting purpose
   * 
   * @param index
   *          the index of the column
   * @return the string representation of the column
   */
  public abstract void setColumn(int index, String value);


  /**
   * Compare the column of given index with the same type input value
   * 
   * @param index
   *          the index of the column to be compared
   * @param value
   *          the input value, which has the same type as column index
   * @return 0 equal, 1 greater than input value, -1 less than input value
   */
  public abstract int compareColumn(int index, String value);
}
