package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class MarkdownFileServlet extends MasterServlet{

    private void convertToFile(Session session, HttpServletRequest req){
        System.out.println(req.getPathInfo());
        File file = null;
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/markdownEditor.jsp").forward(req,resp);
        return ;
    }

}
