package com.boryi.bots.client.data;

import java.util.ArrayList;


/**
 * Data bundle for table list
 * 
 * @author Gang Huang Ph.D.
 * @version v1.0.0
 * @since 2009-09-13
 */
public abstract class TableList
{
  public final static int PAGES = 5;
  protected final static int PAGE_STEP = 3;

  protected int rowsPerPage = 50;

  protected int total = 0;
  protected int totalPages = 0;

  protected int currentPage = 1;
  protected int startPage = 1;

  protected String sort = null;
  protected boolean sortAsc = true;

  protected ArrayList<String> headers = null;

  protected ArrayList<RowItem> rows = null;


  /**
   * Set the number of rows per page to be displayed
   * 
   * @param rowsPerPage
   *          the number of rows per page to be displayed
   */
  public void setRowsPerPage(int rowsPerPage)
  {
    int firstItemIndex = this.rowsPerPage * (currentPage - 1) + 1;

    this.rowsPerPage = rowsPerPage;

    computeTotalPages();

    currentPage = (int) Math.ceil((double) firstItemIndex
        / (double) rowsPerPage);

    computeStartPage();
  }


  /**
   * Get the number of rows per page to be displayed
   * 
   * @return the rowsPerPage the number of rows per page to be displayed
   */
  public int getRowsPerPage()
  {
    return rowsPerPage;
  }


  /**
   * Get the total number of rows
   * 
   * @return the total number of rows
   */
  public int getTotal()
  {
    return total;
  }


  /**
   * Set the total number of rows
   * 
   * @param total
   *          the total number of rows
   */
  public void setTotal(int total)
  {
    this.total = total;
    computeTotalPages();
    currentPage = 1;
    startPage = 1;
  }


  /**
   * Get the current page index
   * 
   * @return the current page index
   */
  public int getCurrentPage()
  {
    return currentPage;
  }


  /**
   * Set the current page index
   * 
   * @param currentPage
   *          the current page index
   */
  public void setCurrentPage(int currentPage)
  {
    if (currentPage > 0 && currentPage <= totalPages)
    {
      this.currentPage = currentPage;
      computeStartPage();
    }
  }


  /**
   * Get the sort string - column name
   * 
   * @return the sort string - column name
   */
  public String getSort()
  {
    return sort;
  }


  /**
   * Set the sort string - column name
   * 
   * @param sort
   *          the sort string - column name
   */
  public void setSort(String sort)
  {
    this.sort = sort;
  }


  /**
   * Is the sort ascending
   * 
   * @return if the sort is ascending
   */
  public boolean isSortAsc()
  {
    return sortAsc;
  }


  /**
   * Set the sort whether to be ascending
   * 
   * @param sortAsc
   *          whether the sort is ascending
   */
  public void setSortAsc(boolean sortAsc)
  {
    this.sortAsc = sortAsc;
  }


  /**
   * Get column names of the table
   * 
   * @return the column names of the table
   */
  public ArrayList<String> getHeaders()
  {
    return headers;
  }


  /**
   * Get the rows of the current page
   * 
   * @return the rows of the current page
   */
  public ArrayList<RowItem> getRows()
  {
    return rows;
  }


  /**
   * Set the rows of the current page
   * 
   * @param rows
   *          the rows of the current page
   */
  public void setRows(ArrayList<RowItem> rows)
  {
    this.rows = rows;
  }


  /**
   * Get the total number of pages
   * 
   * @return the total number of pages
   */
  public int getTotalPages()
  {
    return totalPages;
  }


  /**
   * Whether the first page link is enabled
   * 
   * @return whether the first page link is enabled
   */
  public boolean isFirstEnabled()
  {
    return (currentPage > PAGES);
  }


  /**
   * Whether the previous page link is enabled
   * 
   * @return whether the previous page link is enabled
   */
  public boolean isPreviousEnabled()
  {
    return (currentPage > 1);
  }


  /**
   * Whether the next page link is enabled
   * 
   * @return whether the next page link is enabled
   */
  public boolean isNextEnabled()
  {
    return (currentPage < totalPages);
  }


  /**
   * Whether the last page link is enabled
   * 
   * @return whether the last page link is enabled
   */
  public boolean isLastEnabled()
  {
    return (currentPage > PAGES && currentPage < (totalPages - 2));
  }


  /**
   * Get the start page index to be displayed
   * 
   * @return the start page index to be displayed
   */
  public int getStartPage()
  {
    return startPage;
  }


  /**
   * Get the last page index to be displayed
   * 
   * @return the last page index to be displayed
   */
  public int getEndPage()
  {
    if (totalPages > PAGES)
    {
      return (startPage + PAGES - 1);
    }
    return totalPages;
  }


  /**
   * Order the rows of the table according to the given column (ascending or
   * descending) locally - Bubble sort method
   * 
   * @param columnIndex
   *          index of the column to be sorted
   * @param asc
   *          whether to order in ascending order
   */
  public void orderRowsByColumn(int columnIndex, boolean asc)
  {
    for (int i = 0; i < rows.size(); i++)
    {
      for (int j = 0; j < (rows.size() - i - 1); j++)
      {
        if (((rows.get(j).compareColumn(columnIndex,
            rows.get(j + 1).getColumn(columnIndex)) < 0) && !asc)
            || ((rows.get(j).compareColumn(columnIndex,
                rows.get(j + 1).getColumn(columnIndex)) > 0) && asc))
        {
          switchRows(j, j + 1);
        }
      }
    }
  }


  /**
   * Get the ids of selected id of the rows of the current page. If no ids of
   * bots are selected, all jobs are viewed. If no ids of jobs of selected bots
   * are selected, all tasks of selected bots are viewed. If no ids of tasks of
   * selected jobs and bots are selected, all runs of selected jobs and bots are
   * viewed. If no ides of runs of selected tasks, jobs, and bots are selected,
   * all events of selected tasks, jobs, and bots are selected.
   * 
   * NOTE: No matter how many rows are selected for Jobs, Tasks, and Runs, it
   * means all are selected.
   * 
   * @return the ids of selected rows
   */
  public int[] getSelectedRows()
  {
    ArrayList<Integer> selectedRows = new ArrayList<Integer>();
    for (int i = 0; i < rows.size(); i++)
    {
      if (rows.get(i).isSelected())
      {
        selectedRows.add(Integer.valueOf(rows.get(i).getId()));
      }
    }

    if (selectedRows.size() > 0)
    {
      int[] selRows = new int[selectedRows.size()];

      for (int i = 0; i < selectedRows.size(); i++)
      {
        selRows[i] = selectedRows.get(i);
      }

      return selRows;
    }
    return null;
  }


  /**
   * Compute the total number of pages
   */
  protected void computeTotalPages()
  {
    totalPages = (int) Math.ceil((double) total / (double) rowsPerPage);
  }


  /**
   * Compute the start page to include the current page in display. start page,
   * ..., start page + 4
   */
  protected void computeStartPage()
  {
    if (currentPage > PAGES)
    {
      startPage = PAGE_STEP
          * (int) Math
              .ceil((double) (currentPage - PAGES) / (double) PAGE_STEP) + 1;
      if ((startPage + PAGES - 1) > totalPages)
      {
        startPage = totalPages - PAGES + 1;
      }
    }
    else
    {
      startPage = 1;
    }
  }


  /**
   * Switch row i with row j
   * 
   * @param i
   *          index of row i
   * @param j
   *          index of row j
   */
  private void switchRows(int i, int j)
  {
    RowItem temp = rows.get(i);
    rows.set(i, rows.get(j));
    rows.set(j, temp);
  }
}
