package uk.me.eastmans.sap.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uk.me.eastmans.sap.security.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserNotificationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // We need to get some messages, events etc for this user
            if (authentication.getPrincipal() instanceof CurrentUser) {
                CurrentUser user = (CurrentUser) authentication.getPrincipal();
                // Need to get the messages for this user and output them into the model
                List<String> messages = new ArrayList<>();
                // Add a random number of messages
                int messageCount = (int)(Math.random() * 10);
                for (int i = 0; i < messageCount; i++)
                    messages.add( "Another message" );
                modelAndView.getModel().put("userMessages", messages);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}