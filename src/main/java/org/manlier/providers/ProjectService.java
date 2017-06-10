package org.manlier.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 清单服务
 * Created by manlier on 2017/6/7.
 */
@Service
public class ProjectService implements org.manlier.providers.interfaces.IProjectService {

    private String inboxPrefix;

    @Value("inbox_")
    public void setInboxPrefix(String inboxPrefix) {
        this.inboxPrefix = inboxPrefix;
    }

    @Override
    public String getInboxPrefix() {
        return inboxPrefix;
    }
}
