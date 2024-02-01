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

import java.io.*;
import java.net.*;

/**
 * A Thread which is responsible for sending messages to the IRC server.
 * Messages are obtained from the outgoing message queue and sent
 * immediately if possible.  If there is a flood of messages, then to
 * avoid getting kicked from a channel, we put a small delay between
 * each one.
 *
 */
public class OutputThread extends Thread {
    
    
    /**
     * Constructs an OutputThread for the underlying UblabBot.  All messages
     * sent to the IRC server are sent by this OutputThread to avoid hammering
     * the server.  Messages are sent immediately if possible.  If there are
     * multiple messages queued, then there is a delay imposed.
     * 
     * @param bot The underlying UblabBot instance.
     * @param outQueue The Queue from which we will obtain our messages.
     */
    OutputThread(UblabBot bot, Queue outQueue) {
        _bot = bot;
        _outQueue = outQueue;
        this.setName(this.getClass() + "-Thread");
    }
    
    
    /**
     * A static method to write a line to a BufferedOutputStream and then pass
     * the line to the log method of the supplied UblabBot instance.
     * 
     * @param bot The underlying UblabBot instance.
     * @param out The BufferedOutputStream to write to.
     * @param line The line to be written. "\r\n" is appended to the end.
     * @param encoding The charset to use when encoing this string into a
     *                 byte array.
     */
    static void sendRawLine(UblabBot bot, BufferedWriter bwriter, String line) {
        if (line.length() > bot.getMaxLineLength() - 2) {
            line = line.substring(0, bot.getMaxLineLength() - 2);
        }
        synchronized(bwriter) {
            try {
                bwriter.write(line + "\r\n");
                bwriter.flush();
                bot.log(">>>" + line);
            }
            catch (Exception e) {
                // Silent response - just lose the line.
            }
        }
    }
    
    
    /**
     * This method starts the Thread consuming from the outgoing message
     * Queue and sending lines to the server.
     */
    public void run() {
        try {
            boolean running = true;
            while (running) {
                // Small delay to prevent spamming of the channel
                Thread.sleep(_bot.getMessageDelay());
                
                String line = (String) _outQueue.next();
                if (line != null) {
                    _bot.sendRawLine(line);
                }
                else {
                    running = false;
                }
            }
        }
        catch (InterruptedException e) {
            // Just let the method return naturally...
        }
    }
    
    private UblabBot _bot = null;
    private Queue _outQueue = null;
    
}
