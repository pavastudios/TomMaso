package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.*;
import com.pavastudios.TomMaso.db.queries.entities.BlogQueries;
import com.pavastudios.TomMaso.db.queries.entities.CommentQueries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class BlogEndpoint {
    @Endpoint(url = "/blog/create-dir", requireLogin = true, params = {
            @ApiParameter(name = "parent-dir", type = ApiParam.Type.STRING),
            @ApiParameter(name = "dir-name", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage CREATE_DIR_ACTION = (parser, writer, user) -> {
        String parentDir = parser.getValueString("parent-dir");
        String dirName = parser.getValueString("dir-name");
        Blog blog = Blog.fromPathInfo(parentDir);
        if (blog == null || !blog.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid blog");
        }
        if (dirName.contains("/") || dirName.contains("\\")) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid dir name");
        }
        File file = FileUtility.blogPathToFile(parentDir);
        if (file == null || !file.isDirectory()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid parent-dir name");
        }
        File file2 = new File(file, dirName);
        if (!file2.getParentFile().equals(file)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid dir-name");
        }
        if (!file2.mkdirs()) {
            throw new ApiException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "unable to create the folder");
        }
        writer.name(ApiManager.OK_PROP).value("created");
    };


    @Endpoint(url = "/blog/delete-blog", requireLogin = true, params = {
            @ApiParameter(name = "blog-name", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage DELETE_BLOG_ACTION = (parser, writer, user) -> {
        String name = parser.getValueString("blog-name");
        Blog blog = BlogQueries.findBlogByName(name);
        if (blog == null || !blog.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Blog invalido");
        }
        BlogQueries.deleteBlog(blog);
        CommentQueries.deleteCommentsForBlog("/" + blog.getNome() + "/");
        File rootBlog = new File(FileUtility.BLOG_FILES_FOLDER, blog.getNome());
        FileUtility.recursiveDelete(rootBlog);
        writer.name(ApiManager.OK_PROP).value("ok");
    };

    @Endpoint(url = "/blog/delete-file", requireLogin = true, params = {
            @ApiParameter(name = "url", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage DELETE_FILE_ACTION = (parser, writer, user) -> {
        String url = parser.getValueString("url");
        Blog blog = Blog.fromPathInfo(url);

        if (blog == null || !blog.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Blog invalido");
        }

        File file = FileUtility.blogPathToFile(url);
        if (file == null || !file.exists()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "File invalido");
        }
        FileUtility.recursiveDelete(file);
        CommentQueries.deleteCommentsForBlog(url);
        writer.name(ApiManager.OK_PROP).value("ok");
    };
    @Endpoint(url = "/blog/create", requireLogin = true, params = {
            @ApiParameter(name = "name", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage CREATE_ACTION = (parser, writer, user) -> {
        String name = parser.getValueString("name");
        if (name.length() < Blog.MINIMUM_NAME_LENGTH || !Utility.useOnlyNormalChars(name)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Nome blog non valido");
        }
        try {
            Blog blog = BlogQueries.createBlog(user, name);
            writer.name(ApiManager.OK_PROP);
            blog.writeJson(writer);
            File file = new File(FileUtility.BLOG_FILES_FOLDER, blog.getNome());
            file.mkdir();
        } catch (SQLException ignore) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Nome blog duplicato");
        }
    };

}
