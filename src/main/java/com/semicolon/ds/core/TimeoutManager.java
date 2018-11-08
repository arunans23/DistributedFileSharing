package com.semicolon.ds.core;

import com.semicolon.ds.utils.TimeoutCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class TimeoutManager {
    private final Logger LOG = Logger.getLogger(TimeoutManager.class.getName());
    Map<String, TimeoutCallbackMap> requests = new HashMap<String, TimeoutCallbackMap>();

    public void registerRequest(String messaageId, long timeout, TimeoutCallback callback) {
        requests.put(messaageId, new TimeoutCallbackMap(timeout, callback));
    }

    public void registerResponse(String messageId) {
        requests.remove(messageId);
    }

    public void checkForTimeout() {
        for (String messageId: requests.keySet()) {
            if(requests.get(messageId).checkTimeout(messageId)) {
                if(messageId.equals("routinePing")) {
                    requests.get(messageId).timeoutTime = requests.get(messageId).timeoutTime
                            + requests.get(messageId).timeout;
                }else {
                    requests.remove(messageId);
                }

            }
        }
    }

    private class TimeoutCallbackMap{
        private long timeoutTime;
        private TimeoutCallback callback;
        private long timeout;

        private TimeoutCallbackMap(long timeout, TimeoutCallback callback) {
            this.timeout = timeout;
            this.callback = callback;
            this.timeoutTime = System.currentTimeMillis() + timeout;
        }
        private boolean checkTimeout(String messageID) {
            if (System.currentTimeMillis() >= timeoutTime) {
                callback.onTimeout(messageID);
                return true;
            }
            return false;
        }
    }
}