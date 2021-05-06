package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.ApiEndpoint;
import com.pavastudios.TomMaso.api.ApiGroup;
import com.pavastudios.TomMaso.api.ApiManager;
import com.pavastudios.TomMaso.api.ApiParam;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Utility;

import java.io.File;
import java.sql.SQLException;


public class BlogEndpoint {
    public static final String GROUP_NAME = "blog";
    private static final String CREATE_ENDPOINT_NAME = "create";

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
            )
    );
}
