package org.manlier.providers;

import org.manlier.providers.interfaces.IWebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * TODO: DONE THIS
 * Created by manlier on 2017/6/6.
 */
@Service
public class WebSocketService implements IWebSocketService {

    private Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    public void sendMessage(String msg) {
        logger.info("The service send message for schedule:" + msg);
    }
}
