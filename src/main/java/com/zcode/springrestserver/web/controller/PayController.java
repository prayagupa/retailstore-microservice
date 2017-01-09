package com.zcode.springrestserver.web.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by prayagupd
 * on 12/3/16.
 */

@WebServlet("/payment")
public class PayController extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter w = resp.getWriter();
        w.write("Paid");
        w.flush();
    }
}
