/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.maksim_zakharenka.myapplication.backend;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws IOException {
        final UserService userService = UserServiceFactory.getUserService();
        final User user = userService.getCurrentUser();
        final PrintWriter writer = resp.getWriter();
        if (user != null) {
            resp.sendRedirect("/index.html");
        } else {
            resp.setContentType("text/html");
            writer.println("<h2>GAE - Integrating Google user account</h2>");
            writer.println(
                    "Please <a href='"
                            + userService.createLoginURL(req.getRequestURI())
                            + "'> LogIn </a>");
        }
    }

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws IOException {
        throw new UnsupportedOperationException("doPost is not supported");
    }
}