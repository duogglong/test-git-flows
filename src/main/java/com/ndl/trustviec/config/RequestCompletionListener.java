package com.ndl.trustviec.config;

import com.ndl.trustviec.utils.system.SystemContextHolder;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class RequestCompletionListener implements ApplicationListener<RequestHandledEvent> {

    @Override
    public void onApplicationEvent(RequestHandledEvent event) {
        SystemContextHolder.clear();
    }
}
