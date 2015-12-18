/**
 *  This file is part of the ublab-bot
 *  Copyright (C) 2015  Black Hackers(Elite Programmers Club, University of Buea)
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
package org.ublab.bot;

import java.util.Vector;

/**
 * Queue is a definition of a data structure that may
 * act as a queue - that is, data can be added to one end of the
 * queue and data can be requested from the head end of the queue.
 * This class is thread safe for multiple producers and a single
 * consumer.  The next() method will block until there is data in
 * the queue.
 *
 * This has now been modified so that it is compatible with
 * the earlier JDK1.1 in order to be suitable for running on
 * mobile appliances.  This means replacing the LinkedList with
 * a Vector, which is hardly ideal, but this Queue is typically
 * only polled every second before dispatching messages.
 * 
 */
public class Queue {
    

    /**
     * Constructs a Queue object of unlimited size.
     */
    public Queue() {
        
    }
    
    
    /**
     * Adds an Object to the end of the Queue.
     *
     * @param o The Object to be added to the Queue.
     */
    public void add(Object o) {
        synchronized(_queue) {
            _queue.addElement(o);
            _queue.notify();
        }
    }
    
    
    /**
     * Adds an Object to the front of the Queue.
     * 
     * @param o The Object to be added to the Queue.
     */
    public void addFront(Object o) {
        synchronized(_queue) {
            _queue.insertElementAt(o, 0);
            _queue.notify();
        }
    }
    
    
    /**
     * Returns the Object at the front of the Queue.  This
     * Object is then removed from the Queue.  If the Queue
     * is empty, then this method shall block until there
     * is an Object in the Queue to return.
     *
     * @return The next item from the front of the queue.
     */
    public Object next() {
        
        Object o = null;
        
        // Block if the Queue is empty.
        synchronized(_queue) {
            if (_queue.size() == 0) {
                try {
                    _queue.wait();
                }
                catch (InterruptedException e) {
                    return null;
                }
            }
        
            // Return the Object.
            try {
                o = _queue.firstElement();
                _queue.removeElementAt(0);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw new InternalError("Race hazard in Queue object.");
            }
        }

        return o;
    }
    
    
    /**
     * Returns true if the Queue is not empty.  If another
     * Thread empties the Queue before <b>next()</b> is
     * called, then the call to <b>next()</b> shall block
     * until the Queue has been populated again.
     *
     * @return True only if the Queue not empty.
     */
    public boolean hasNext() {
        return (this.size() != 0);
    }
    
    
    /**
     * Clears the contents of the Queue.
     */
    public void clear() {
        synchronized(_queue) {
            _queue.removeAllElements();
        }
    }
    
    
    /**
     * Returns the size of the Queue.
     *
     * @return The current size of the queue.
     */
    public int size() {
        return _queue.size();
    }
    

    private Vector _queue = new Vector();
    
}
