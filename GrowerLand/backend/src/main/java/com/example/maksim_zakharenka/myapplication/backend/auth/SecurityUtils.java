
package com.example.maksim_zakharenka.myapplication.backend.auth;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SecurityUtils {

    public static boolean hasPermission(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final UserService userService = UserServiceFactory.getUserService();
        final User user = userService.getCurrentUser();
        if (user != null && user.getEmail().equals("mv.maxcriser@gmail.com")) {
            return true;
        }
        resp.sendRedirect("/auth");
        return false;
    }
}