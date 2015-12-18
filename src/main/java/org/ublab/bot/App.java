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

import org.ublab.bot.*;

/**
 * #ublab IRC bot
 *
 */
public class App extends UblabBot {

	public App() {
		this.setName("ublab-bot");
	}
	
    public static void main( String[] args ) {

    	// Starts ublab-bot
    	App bot = new App();
    	
    	try {
    		//Enables debugging output.
    		bot.connect("irc.freenode.net");
    	
    		//Join #ublab channel.
    		bot.joinChannel("#ublab");
    		
    	} catch (Exception ex) {
    		System.exit(1);
    	}
    	
    	
        
    }
}
