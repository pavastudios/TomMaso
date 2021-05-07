package com.pavastudios.TomMaso.api.groups;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.api.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class BlogEndpoint {
    public static final String GROUP_NAME = "blog";
    private static final String CREATE_ENDPOINT_NAME = "create";
    private static final String DELETE_ENDPOINT_NAME = "delete-blog";

    private static final ApiEndpoint.Manage DELETE_ACTION = (parser, writer, user) -> {
        int id = parser.getValueInt("id");
        writer.name("deleted").value("OK");
        Blog blog = Queries.findBlogById(id);
        Queries.deleteBlog(blog, user);
        File blogDir = new File(FileUtility.BLOG_FILES_FOLDER,blog.getNome());
        FileUtility.deleteDir(blogDir);
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
            ),
            new ApiEndpoint(DELETE_ENDPOINT_NAME, true, DELETE_ACTION,
                    new ApiParam("id", ApiParam.Type.INT)
            )
    );


}
