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

import java.net.URL;
import java.text.MessageFormat;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Created By: Arat Rana
 * This websocketconfigurator is to validate the url for cross origin and to get the user
 */
public class WebSocketConfigurator extends ServerEndpointConfig.Configurator  {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfigurator.class);

    private boolean isValidHost;

    @Override
    public boolean checkOrigin(String originHeaderValue) {
        try {
            URL url = new URL(originHeaderValue);
            String hostName = url.getHost();
            
            if(logger.isInfoEnabled())
                logger.info("websocket connection from host "+hostName);
        } catch (Exception ex){
            logger.error("Error in check checkOrigin for websocket call: "+ex.getMessage());
        }
        return true;
    }

    /**
     *
     * @param config
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(config, request, response);
    }

    /**
     *
     * @param endpointClass
     * @param <T>
     * @return
     * @throws InstantiationException
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        //HttpServletRequest req = RequestContext.getCurrentInstance().getRequest();
        //HttpSession httpSession = req.getSession();
        if (endpoint instanceof NotificationSocket) {
            //String user = httpSession.getAttribute("CSM_USER_NAME").toString();
        	String user = "System";
            //set the httpsession to get the username in handleMessage
            ((NotificationSocket) endpoint).setUserName(user);
            if(logger.isInfoEnabled())
                logger.info("websocket call from user "+user);
        } else {
            logger.warn(MessageFormat.format("Expected instanceof \"{0}\". Got instanceof \"{1}\".",
                    NotificationSocket.class, endpoint.getClass()));
        }
        return endpoint;
    }
}
