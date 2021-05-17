package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Utility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;


public class BlogEndpoint {
    public static final String GROUP_NAME = "blog";
    private static final String CREATE_ENDPOINT_NAME = "create";
    private static final String DELETE_ENDPOINT_NAME = "delete";
    private static final String DELETE_BLOG_ENDPOINT_NAME = "delete-blog";
    private static final String MOVE_ENDPOINT_NAME = "move";
    private static final String RENAME_ENDPOINT_NAME = "rename";

    private static final ApiEndpoint.Manage MOVE_ACTION= (parser, writer, user) -> {
        String from=parser.getValueString("from-url");
        String to=parser.getValueString("to-url");

        File fromFile=FileUtility.blogPathToFile(from);
        File toFile=FileUtility.blogPathToFile(to);
        if(fromFile==null||toFile==null||!fromFile.exists()||toFile.exists()||!toFile.getParentFile().exists()){
            writer.name(ApiManager.ERROR_PROP).value("Invalid paths");
            return;
        }
        Blog fromBlog=Blog.fromPathInfo(from);
        Blog toBlog=Blog.fromPathInfo(to);
        if(fromBlog==null||toBlog==null||!fromBlog.getProprietario().equals(user)||!toBlog.getProprietario().equals(user)){
            writer.name(ApiManager.ERROR_PROP).value("Blog invalidi");
            return;
        }
        Files.move(fromFile.toPath(),toFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        writer.name("response").value("ok");
    };

    private static final ApiEndpoint.Manage DELETE_BLOG_ACTION= (parser, writer, user) -> {
        String name=parser.getValueString("blog-name");
        Blog blog=Queries.findBlogByName(name);
        if(blog==null||!blog.getProprietario().equals(user)){
            writer.name(ApiManager.ERROR_PROP).value("Blog invalido");
            return;
        }
        Queries.deleteBlog(blog);
        File rootBlog=new File(FileUtility.BLOG_FILES_FOLDER,blog.getNome());
        FileUtility.recursiveDelete(rootBlog);
        writer.name("response").value("ok");
    };

    private static final ApiEndpoint.Manage RENAME_ACTION = (parser, writer, user) -> {
        String fromName=parser.getValueString("from-name");
        String toName=parser.getValueString("to-name");
        if(!Utility.useOnlyNormalChars(toName)){
            writer.name(ApiManager.ERROR_PROP).value("Nuovo nome invalido");
            return;
        }
        Blog fromBlog=Queries.findBlogByName(fromName);
        Blog toBlog=Queries.findBlogByName(toName);
        if(toBlog!=null){
            writer.name(ApiManager.ERROR_PROP).value("Blog già esistente");
            return;
        }
        if(fromBlog==null||!fromBlog.getProprietario().equals(user)){
            writer.name(ApiManager.ERROR_PROP).value("Blog invalido");
            return;
        }
        File newRootBlog=new File(FileUtility.BLOG_FILES_FOLDER,toName);
        File oldRootBlog=new File(FileUtility.BLOG_FILES_FOLDER,fromName);
        Queries.renameBlog(fromBlog,toName);
        Files.move(oldRootBlog.toPath(),newRootBlog.toPath(), StandardCopyOption.REPLACE_EXISTING);
        writer.name("response").value("ok");
    };

    private static final ApiEndpoint.Manage DELETE_ACTION = (parser, writer, user) -> {
        String url=parser.getValueString("url");
        Blog blog=Blog.fromPathInfo(url);

        if(blog==null||!blog.getProprietario().equals(user)){
            writer.name(ApiManager.ERROR_PROP).value("Blog invalido");
            return;
        }

        File file=FileUtility.blogPathToFile(url);
        if(file==null||!file.exists()){
            writer.name(ApiManager.ERROR_PROP).value("File invalido");
            return;
        }
        FileUtility.recursiveDelete(file);
        writer.name("response").value("ok");
    };

    private static final ApiEndpoint.Manage FETCH_ACTION = (parser, writer, user) -> {
        String name = parser.getValueString("name");
        if (name.length() < Blog.MINIMUM_NAME_LENGTH || !Utility.useOnlyNormalChars(name)) {
            writer.name(ApiManager.ERROR_PROP).value("Nome blog non valido");
            return;
        }
        try {
            Blog blog = Queries.createBlog(user, name);
            writer.name("response");
            blog.writeJson(writer);
            File file = new File(FileUtility.BLOG_FILES_FOLDER, blog.getNome());
            file.mkdir();
        } catch (SQLException ignore) {
            writer.name(ApiManager.ERROR_PROP).value("Nome blog duplicato");
        }
    };

    public static final ApiGroup ENDPOINTS = new ApiGroup(GROUP_NAME,
        new ApiEndpoint(CREATE_ENDPOINT_NAME, true, FETCH_ACTION,
                new ApiParam("name", ApiParam.Type.STRING)
        ), new ApiEndpoint(DELETE_ENDPOINT_NAME, true, DELETE_ACTION,
            new ApiParam("url", ApiParam.Type.STRING)
        ), new ApiEndpoint(DELETE_BLOG_ENDPOINT_NAME, true, DELETE_BLOG_ACTION,
            new ApiParam("blog-name", ApiParam.Type.STRING)
        ),new ApiEndpoint(MOVE_ENDPOINT_NAME,true,MOVE_ACTION,
            new ApiParam("from-url", ApiParam.Type.STRING),
            new ApiParam("to-url", ApiParam.Type.STRING)
        ),new ApiEndpoint(RENAME_ENDPOINT_NAME,true,RENAME_ACTION,
            new ApiParam("from-name", ApiParam.Type.STRING),
            new ApiParam("to-name", ApiParam.Type.STRING)
        )
    );


}
