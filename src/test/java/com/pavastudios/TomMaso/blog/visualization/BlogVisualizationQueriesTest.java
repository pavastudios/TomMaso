package com.pavastudios.TomMaso.blog.visualization;

import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.storage.model.Utente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import utility.TestDBConnection;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class BlogVisualizationQueriesTest extends TestDBConnection {

    private static Stream<Arguments> blogsUserGenerator() {
        return Stream.of(
                Arguments.of(1, new int[]{}),
                Arguments.of(2, new int[]{1, 3}),
                Arguments.of(3, new int[]{2}),
                Arguments.of(4, new int[]{})
        );
    }

    @ValueSource(ints = {-2, 0})
    @ParameterizedTest
    void topBlogsEmpty() throws SQLException {
        List<Blog> blogs;
        blogs = BlogVisualizationQueries.topBlogs(0);
        Assertions.assertEquals(0, blogs.size());
    }

    @Test
    void topBlogs() throws SQLException {
        List<Blog> blogs = BlogVisualizationQueries.topBlogs(999);
        List<Blog> blogsLimit = BlogVisualizationQueries.topBlogs(3);
        Assertions.assertEquals(blogsLimit, blogs);
        List<Blog> sorted = blogs.stream().sorted((o1, o2) -> o2.getVisite() - o1.getVisite()).collect(Collectors.toList());
        Assertions.assertEquals(3, blogs.size());
        Assertions.assertEquals(sorted, blogs);
    }

    @MethodSource("blogsUserGenerator")
    @ParameterizedTest
    void getBlogsUser(int idUser, int[] blogIds) throws SQLException {
        Utente user = Mockito.mock(Utente.class);
        Mockito.when(user.getIdUtente()).thenReturn(idUser);
        List<Blog> userBlogs = BlogVisualizationQueries.getBlogsUser(user);
        Assertions.assertTrue(userBlogs.stream().map(Blog::getIdBlog).allMatch(integer -> Arrays.stream(blogIds).anyMatch(value -> value == integer)));
    }

    @Test
    void getBlogsUserNull() throws SQLException {
        List<Blog> userBlogs = BlogVisualizationQueries.getBlogsUser(null);
        Assertions.assertEquals(0, userBlogs.size());
    }

    @ValueSource(ints = {1, 2, 3})
    @ParameterizedTest
    void incrementVisit(int blogId) throws SQLException {
        Blog oldBlog = BlogQueries.findBlogById(blogId);
        BlogVisualizationQueries.incrementVisit(oldBlog);
        Blog newBlog = BlogQueries.findBlogById(blogId);
        Assertions.assertEquals(newBlog.getVisite(), oldBlog.getVisite() + 1);
    }

    @Test
    void incrementVisitNull() throws SQLException {
        BlogVisualizationQueries.incrementVisit(null);
    }
}