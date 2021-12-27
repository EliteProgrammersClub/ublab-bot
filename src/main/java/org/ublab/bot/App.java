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

import java.util.logging.Logger;

/**
 * #ublab IRC bot
 *
 */
public class App extends UblabBot {

    private static final String IRC_NETWORK = "irc.freenode.net";
    private static final String IRC_CHANNEL = "#ublab";

    public App() {
        this.setName("ublab-bot");
    }

    public static void main( String[] args ) {

        Logger logger = Logger.getLogger(App.class.getName());

        // Starts ublab-bot
        App bot = new App();

        try {
            //Enables debugging output.
            bot.connect(IRC_NETWORK);
            logger.info("Connected to " + IRC_NETWORK);
            //Join #ublab channel.
            bot.joinChannel(IRC_CHANNEL);
            logger.info("Joined " + IRC_CHANNEL);

        } catch (Exception ex) {
            logger.warning("Caught exception. Closing...");
            ex.printStackTrace();
            System.exit(1);
        }

    }
}