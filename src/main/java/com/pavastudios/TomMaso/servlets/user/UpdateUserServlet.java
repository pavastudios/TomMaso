package com.pavastudios.TomMaso.servlets.user;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@MultipartConfig(
        maxFileSize = UpdateUserServlet.MAX_PROPIC_SIZE,
        maxRequestSize = UpdateUserServlet.MAX_REQUEST_SIZE
)
public class UpdateUserServlet extends MasterServlet {
    static final int MAX_PROPIC_SIZE=1024*1024*5;
    static final int MAX_REQUEST_SIZE=1024*1024*6;
    static final int PROPIC_SIZE=512;
    private BufferedImage toPropic(BufferedImage image){
        if(image==null)return null;
        Image tmp=image.getScaledInstance(PROPIC_SIZE,PROPIC_SIZE,BufferedImage.SCALE_SMOOTH);
        BufferedImage img=new BufferedImage(PROPIC_SIZE,PROPIC_SIZE,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d=img.createGraphics();
        g2d.drawImage(tmp,0,0,null);
        g2d.dispose();
        return img;
    }
    private void elaboratePropic(Utente user,Part part) throws IOException {
        if(part==null||part.getSize()==0)return;
        File propicFile=File.createTempFile("propic-",".png",FileUtility.TMP_FOLDER);
        boolean written=false;
        try {
            BufferedImage image = ImageIO.read(part.getInputStream());
            image=toPropic(image);
            written = ImageIO.write(image, "png", propicFile);
        }catch(IllegalArgumentException ignore){ }
        if(written){
            File oldPropic=user.getPropicFile();
            Files.move(propicFile.toPath(),oldPropic.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente user=session.getUtente();
        String newUsername=req.getParameter("username");
        String bio=req.getParameter("bio");
        String oldPassword=req.getParameter("oldpsw");
        String newPassword1=req.getParameter("newpsw1");
        String newPassword2=req.getParameter("newpsw2");
        Part part=req.getPart("propic");
        if(user==null||newUsername==null||bio==null||oldPassword==null||newPassword1==null||newPassword2==null||part==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Parametri mancanti");
            return;
        }

        if(!Utility.useOnlyNormalChars(newUsername)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Nuovo username invalido");
            return;
        }

        if(!updatePassword(user,oldPassword,newPassword1,newPassword2)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Impossibile cambiare password");
            return;
        }
        elaboratePropic(user,part);//update profile pic
        Queries.updateUser(user,newUsername,bio);//update username e bio
        Utente updatedUser=Queries.findUserById(user.getIdUtente());
        session.setUtente(updatedUser);
        if(!user.getUsername().equals(updatedUser.getUsername()))//cambia cartella per l'utente
            Files.move(user.getUserFolder().toPath(),updatedUser.getUserFolder().toPath(), StandardCopyOption.REPLACE_EXISTING);
        resp.sendRedirect(resp.encodeRedirectURL(req.getServletContext().getContextPath()+"/profile"));
    }
    /**
     * @return true if handled without errors, false otherwise
     * */
    private boolean updatePassword(Utente user, String oldPassword, String newPassword1, String newPassword2) {
        if(oldPassword.isEmpty())return true;//Controlla se si vuole cambiare password
        if(!user.userVerifyLogin(oldPassword)){//vecchia password errata
            return false;
        }
        if(!newPassword1.equals(newPassword2)){//nuove password diverse
            return false;
        }
        if(!newPassword1.isEmpty()){//Esegui solo se la nuova password non è vuota
            try {
                Queries.changePassword(user,newPassword1);
            } catch (SQLException ignore) {
                return false;
            }
        }
        return true;
    }
}
