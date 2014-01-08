package com.boryi.bots.base;

import java.util.ArrayList;
import java.util.Random;

/**
 * Base class of bot check list
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 * 
 * @author Gang Huang, Ph.D.
 * @version 1.0
 * @since August 2009
 */
public class BotChecklist 
{
    /**
     * Checklist items to be crawled
     */
    protected ArrayList<BotChecklistEntity> checklist;
    /**
     * Checklist items are being crawled
     */
    protected ArrayList<BotChecklistEntity> crawlingList;
    /**
     * Randomly get checklist item
     */
    protected Random random = new Random();
    /**
     * Singleton for checklist which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     */
    protected static BotChecklist instance;

    
    /**
     * Default constructor to create checklist
     */
    public BotChecklist()
    {
        initialize();
        
        checklist = new ArrayList<BotChecklistEntity>();
        crawlingList = new ArrayList<BotChecklistEntity>();
    }
    
    
    /**
     * The first initialization instance. It can refer to the derived class 
     * instance.
     */
    protected void initialize()
    {
        if (instance == null)
        {
            instance = this;
        }
    }

    
    /**
     * Singleton for checklist which can be inherited to refer the derived 
     * class instance.  Always refer to the first initialization instance.
     * 
     * @return The first initialization instance
     */
    public static BotChecklist getInstance()
    {
        return instance;
    }
    
    
    /**
     * Add a new checklist to be crawled
     * 
     * @param checklist A new checklist to be appended and crawled
     */
    public synchronized void addChecklist(ArrayList<BotChecklistEntity> checklist)
    {
        for (int i = 0; i < checklist.size(); i++)
        {
            this.checklist.add(checklist.get(i));
        }
    }
    
    
    /**
     * Get a checklist item to be crawled from checklist randomly
     * 
     * @return a checklist item
     */
    public synchronized BotChecklistEntity getChecklistItem()
    {
        if (!checklist.isEmpty())
        {
            int index = random.nextInt(checklist.size());
            BotChecklistEntity item = checklist.remove(index);
            crawlingList.add(item);
            return item;
        }
        return null;
    }
    
    
    /**
     * Remove a checklist item, which finished crawling, from crawlingList
     * 
     * @param item A checklist item
     */
    public synchronized void removeChecklistItem(BotChecklistEntity item)
    {
        crawlingList.remove(item);
    }
    
    
    /**
     * Whether checklist and crawlingList are empty
     * 
     * @return true if finished checklist and crawling list
     */
    public synchronized boolean IsFinished()
    {
        return (checklist.isEmpty() && crawlingList.isEmpty());
    }
}
