package com.pavastudios.TomMaso.blog.management;

import com.pavastudios.TomMaso.api.ApiAction;
import com.pavastudios.TomMaso.api.ApiException;
import com.pavastudios.TomMaso.api.Endpoint;
import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.blog.visualization.CommentQueries;
import com.pavastudios.TomMaso.storage.FileUtility;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class BlogEndpoint {

    @Endpoint(url = "/blog/delete-blog", requireLogin = true)
    private static final ApiAction DELETE_BLOG_ACTION = (parser, writer, user) -> {
        String name = parser.getValueString("blog-name");
        Blog blog = BlogQueries.findBlogByName(name);
        if (blog == null || !blog.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Blog invalido");
        }
        BlogQueries.deleteBlog(blog);
        CommentQueries.deleteCommentsForBlog("/" + blog.getNome() + "/");
        File rootBlog = new File(FileUtility.BLOG_FILES_FOLDER, blog.getNome());
        FileUtility.recursiveDelete(rootBlog);
        writer.value("ok");
    };

    @Endpoint(url = "/blog/delete-file", requireLogin = true)
    private static final ApiAction DELETE_FILE_ACTION = (parser, writer, user) -> {
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
        writer.value("ok");
    };
    @Endpoint(url = "/blog/create", requireLogin = true)
    private static final ApiAction CREATE_ACTION = (parser, writer, user) -> {
        String name = parser.getValueString("name");
        if (name.length() < Blog.MINIMUM_NAME_LENGTH || !Utility.useOnlyNormalChars(name)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Nome blog non valido");
        }
        try {
            Blog blog = BlogQueries.createBlog(user, name);
            blog.writeJson(writer);
            File file = new File(FileUtility.BLOG_FILES_FOLDER, blog.getNome());
            file.mkdir();
        } catch (SQLException ignore) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Nome blog duplicato");
        }
    };

}
