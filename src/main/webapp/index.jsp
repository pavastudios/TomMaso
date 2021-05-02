<%@ page import="com.pavastudios.TomMaso.utility.RememberMeUtility" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.pavastudios.TomMaso.db.queries.Queries" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (session.isNew()) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (RememberMeUtility.COOKIE_REMEMBER_ME.equals(c.getName())) {
                Utente u = Queries.findUserByCookie(c.getValue());
                if (u != null)
                    session.setAttribute(RememberMeUtility.SESSION_USER, u);
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>TomMASO Homepage</title>
    <meta charset="utf-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"
            integrity="sha512-dqw6X88iGgZlTsONxZK9ePmJEFrmHwpuMrsUChjAw1mRUhUITE5QU9pkcSox+ynfLhL15Sv2al5A0LVyDCmtUw=="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"
          integrity="sha512-8bHTC73gkZ7rZ7vpqUQThUDhqcNFyYi2xgDgPDHc+GXVGHXq+xPjynxIopALmOPqzo9JZj0k6OqqewdGO3EsrQ=="
          crossorigin="anonymous"/>
    <script href="./script/marked.min.js"></script>
    <script href="./script/purify.js"></script>
    <link rel="stylesheet" href="./styles/materialdesignicons.min.css"/>
    <link rel="icon" href="./images/logo.ico" type="image/x-icon"/>
</head>
<body>
<!-- Navbar -->
<div class="ui teal inverted menu">
    <a href="#" class="header item">
        <img src="./images/logo.png" alt="Logo" srcset="">
        TomMASO
    </a>
    <a href="#" class="item">Chi siamo</a>
    <a href="#" class="item">Top blog</a>
    <div class="right menu">
        <a href="#" class="item"><i class="fitted moon icon"></i></a>
        <% Utente u = (Utente) session.getAttribute(RememberMeUtility.SESSION_USER);
            if (u != null) { %>
        <%=u.getUsername()%>
        <% } else { %>
        <a href="./login.jsp" class="item">Login</a>
        <a href="./register.jsp" class="item">Register</a>
        <%}%>
    </div>
</div>
<!-- First Panel -->
<div class="ui vertical stripe segment">
    <div class="ui middle aligned stackable grid container">
        <div class="row">
            <div class="eight wide column">
                <h3 class="ui header">TomMaso</h3>
                <p>
                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Facere modi quas odio fuga, dolorem,
                    asperiores accusantium qui corporis totam unde ipsa numquam eligendi voluptates deleniti culpa
                    distinctio delectus officia sequi.
                    Dolore earum, aspernatur assumenda minus illo dolores ullam asperiores neque! Voluptatum corporis
                    adipisci voluptates perferendis nobis harum facilis ratione neque fuga ullam labore deserunt,
                    cupiditate modi odio architecto placeat delectus.
                    Culpa odio odit praesentium tenetur, veniam sint maiores cumque iste, ipsam porro pariatur qui
                    ullam! Et repudiandae deserunt officia? Earum corrupti ratione odio quidem? Magnam, eius labore.
                    Ipsum, accusantium molestiae!
                </p>
            </div>
            <div class="six wide right floated column">
                <img src="./images/logo.png" alt="" class="hidden content">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <div class="ui teal animated button" tabindex="0">
                    <div class="visible content">Inizia subito</div>
                    <div class="hidden content">
                        <i class="right arrow icon"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- FOOTer -->
<div class="ui vertical teal inverted footer segment">
    <div class="ui container">
        <div class="ui stackable divided equal height grid">
            <!-- Chi siamo -->
            <div class="four wide column">
                <h4 class="ui header inverted">Chi siamo</h4>
                <div class="ui link list inverted">
                    <a href="" class="item inverted">Link 1</a>
                    <a href="" class="item inverted">Link 2</a>
                    <a href="" class="item inverted">Link 3</a>
                    <a href="" class="item inverted">Link 4</a>
                    <a href="" class="item inverted">Link 5</a>
                </div>
            </div>
            <!-- Social link -->
            <div class="four wide column">
                <h4 class="ui header inverted">Social</h4>
                <div class="ui list">
                    <button class="ui facebook button ">
                        <i class="facebook icon"></i>
                        Facebook
                    </button>
                    <button class="ui twitter button">
                        <i class="twitter icon"></i>
                        Twitter
                    </button>
                    <button class="ui instagram button">
                        <i class="instagram icon"></i>
                        Instagram
                    </button>
                </div>
            </div>
            <!-- Contatti -->
            <div class="four wide column ">
                <h4 class="ui header inverted">Contatti</h4>
                <div class="ui inverted relaxed divided list">
                    <!-- Cellphone number -->
                    <a href="#" class="item">
                        <i class="phone icon"></i>
                        <div class="content">
                            <div class="header inverted">Cellphone</div>
                            <div class="description inverted">
                                +39-333.666.999
                            </div>
                        </div>
                    </a>
                    <!-- Email -->
                    <a href="#" class="item">
                        <i class="mail icon"></i>
                        <div class="content">
                            <div class="header inverted">Email</div>
                            <div class="description inverted">
                                tommasoblog@fakemail.com
                            </div>
                        </div>
                    </a>
                    <!-- Reddit -->
                    <a href="#" class="item ">
                        <i class="reddit icon"></i>
                        <div class="content">
                            <div class="inverted header">Subreddit</div>
                            <div class="description white">
                                r/tomMASO
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div class="ui center aligned container" style="margin-top:50px;">
        <h5 class="inverted">
            <i class="copyright icon "></i>
            Copyright 2021 - P.A.V.A. Studios
        </h5>
    </div>
</div>
</body>
</html>