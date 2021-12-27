<%@ page import="java.util.List" %>
<%@ page import="com.pavastudios.TomMaso.storagesystem.model.Report" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.pavastudios.TomMaso.utility.Utility" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.pavastudios.TomMaso.accesssystem.UserQueries" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="org.jsoup.nodes.Entities" %><%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 21/12/21
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Report - TomMASO</title>
    <%@ include file="../general/headTags.jsp" %>
</head>
<body>


<%@ include file="../general/navbar.jsp" %>
<%
    List<Report>reports= (List<Report>) request.getAttribute("REPORTS");
%>
<div class="container mt-md-5">
    <div class="list-group">

        <% for(Report report:reports){ %>

        <div  class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">Riportato da
                    <a href="${pageContext.request.contextPath}/user/<%=report.getReporter().getUsername()%>"><%=report.getReporter().getUsername()%></a>
                     (<%=report.getType()%>)
                </h5>
                <small><%=Utility.DATE_FORMAT.format(report.getReportDate())%></small>

            </div>
            <div class="float-end">
                <button id="approve-<%=report.getIdReport()%>" type="button" class="approve btn btn-success"><i class="fas fa-check"></i> </button>
                <button id="disapprove-<%=report.getIdReport()%>" type="button" class="disapprove btn btn-danger"><i class="fas fa-trash"></i></button>
            </div>
            <p class="mb-1"> <%=Entities.escape(report.getReason())%></p>
            <small><a href="<%=report.getUrl()%>"> <%=report.getUrl()%></a></small>
        </div>

<%}%>

    </div>
</div>



<%@ include file="../general/footer.jsp"%>
<%@ include file="../general/tailTag.jsp"%>
<script>
    function reviewReport(id,approved){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/report/review<%=request.getAttribute("rewrite")%>',
            data: {
                "id-report": id,
                "approved": approved,
            },
            success: function (data) {
                console.log(data);
                navbarLogin.hide();
                location.reload();
            }
        });
    }
    $(".approve").on("click",function () {
        let id=parseInt($(this)[0].id.split("-")[1]);
        reviewReport(id,true);
    });
    $(".disapprove").on("click",function () {
        let id=parseInt($(this)[0].id.split("-")[1]);
        reviewReport(id,false);
    });
</script>
</body>
</html>
