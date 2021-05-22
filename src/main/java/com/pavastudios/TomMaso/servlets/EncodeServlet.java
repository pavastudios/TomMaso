package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet(name="encoder",urlPatterns = {"/encode"})
public class EncodeServlet extends MasterServlet{
    private static String encode(HttpServletRequest req,HttpServletResponse resp){
        String url=req.getParameter("url");
        if(url==null)return "";
        return resp.encodeURL(url);
    }
    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String encoded=encode(req,resp);
        System.out.println(encoded);
        resp.getWriter().write(encoded);
    }
}
