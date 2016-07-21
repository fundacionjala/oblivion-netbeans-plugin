/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.output;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.awt.Notification;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.IOColorLines;
import org.openide.windows.IOColors;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

@NbBundle.Messages({"OutputHandler_salesforceMessage=Salesforce message",
    "OutputHandler_salesforceWarning=Salesforce warning",
    "OutputHandler_salesforceSuccess=Salesforce success",
    "OutputHandler_salesforceDanger=Salesforce danger"
})

/**
 * This class handles the output of messages to the output tabs within the
 * platform.
 *
 * @author sergio_daza
 */
public final class OutputHandler implements PropertyChangeListener  {
    private static final String ICON = "org/fundacionjala/oblivion/salesforce/resources/SalesforceProject_Icon.png";
    private static final InputOutput io = IOProvider.getDefault().getIO("Salesforce",false);
    private static final Icon ICON_SALESFORCE = new ImageIcon(ImageUtilities.loadImage(ICON));
    private static final List<Notification> notifications = new ArrayList<Notification>();
   
    private OutputHandler() {
    }

    /**
     * This method shows a message in a netbeans pop (notification display).
     * 
     * @param title
     * @param message 
     */
    public static void notify(String title, String message){
        notifications.add(NotificationDisplayer.getDefault().notify(title, ICON_SALESFORCE, message, null,NotificationDisplayer.Priority.LOW));
    }
    
    // Removes notifications that it was made with the OutputHandler class.
    public static void removeAllNotify() {
        for (Notification notification : notifications) {
            notification.clear();
        }
    }
    
    /**
     * Update the message in the status windows of the platform
     *
     * @param message
     */
    public static void setStatus(String message) {
        StatusDisplayer.getDefault().setStatusText(message);
    }
    
    /**
     * This method show a common message in output tab.
     * 
     * @param message 
     */
    public static void text(String message) {
        output(message, IOColors.getColor(io, IOColors.OutputType.OUTPUT));
        setStatus(Bundle.OutputHandler_salesforceMessage());
    }
    
    /**
     * This method show a warning message in output tab.
     * 
     * @param message 
     */
    public static void warning(String message) {
        output(message, IOColors.getColor(io, IOColors.OutputType.LOG_WARNING));
        setStatus(Bundle.OutputHandler_salesforceWarning());
    }
   
    /**
     * This method show a success message in output tab.
     * 
     * @param message 
     */
    public static void success(String message) {
        output(message, IOColors.getColor(io, IOColors.OutputType.LOG_SUCCESS));
        setStatus(Bundle.OutputHandler_salesforceSuccess());
    }
    
    /**
     * This method show a danger message in output tab.
     * 
     * @param message 
     */
    public static void danger(String message) {
        output(message, IOColors.getColor(io, IOColors.OutputType.ERROR));
        setStatus(Bundle.OutputHandler_salesforceDanger());
    }
    
    /**
     * This method show a message with the neccesary color  for the message 
     * type also put focus in the output tab.
     * 
     * @param message
     * @param color 
     */
    public static void output(String message, Color color) {
        try {
            IOColorLines.println(io, message, color);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Notification> toRemove;
        synchronized (this) {
            toRemove = new ArrayList<Notification>(notifications);
            notifications.clear();
}
        for (Notification notification : toRemove) {
            notification.clear();
        }
    }
}
