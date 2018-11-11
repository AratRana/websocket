//=========================================================================================================
//			Copyright <1995-2018> JDA Software Group, Inc. All rights reserved.
//			LICENSE OF THIS PROGRAM IS ONLY ENTITLED TO ACCESS THE CONFIGURATION(S) SPECIFIED IN ITS
//			SOFTWARE LICENSE AGREEMENT WITH JDA.  ACCESS OF ANY OTHER CONFIGURATION IS A DIRECT VIOLATION
//			OF THE TERMS OF THE SOFTWARE LICENSE AGREEMENT, AND JDA RETAINS ALL ITS LEGAL RIGHTS TO ENFORCE
//			SUCH AGREEMENT.
//			This product may be protected by one or more United States and foreign patents.
//			For information on patents, see https://www.jda.com/legal/patents.
//=========================================================================================================
package com.jda.webworks.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Created By: Arat Rana
 * Websocket to push notification count
 */
@ServerEndpoint(value = "/notificationcount", configurator = WebSocketConfigurator.class)
public class NotificationSocket {
    private static final Logger logger = LoggerFactory.getLogger(NotificationSocket.class);

    private String userName;
    
    static int count = 0;

    /**
     *
     * @param userName
     *
     */
    public void setUserName(String userName) {
        if (this.userName != null) {
            if(logger.isInfoEnabled())
                logger.info("HttpSession has already been set for websocket call!");
        }

        this.userName = userName;
    }

    /**
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        if(logger.isInfoEnabled())
            logger.info("opened websocket for user "+userName);
    }

    @OnClose
    public void onClose() {
        if(logger.isInfoEnabled())
            logger.info("websocket connection closed");
    }

    /**
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void error(Session session, Throwable throwable) {
        logger.error("Error in websocket connection");
    }

    /**
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void handleMessage(String message, final Session session) {
        synchronized (session) {
            if (session != null && session.isOpen()) {
                
               // NotificationManagerDAO notificationManagerDAO = NotificationManagerDAO.getNotificationManagerDAO();
                try {
                   count = count+1;
                    if(logger.isInfoEnabled())
                        logger.info("Notification count for user" + userName + "= " + count);
                } catch (Exception e) {
                    logger.error("Exception in getting notification count", e);
                }
                session.getAsyncRemote().sendText("" + count);
                session.setMaxIdleTimeout(-1);
            }
        }
    }
}
