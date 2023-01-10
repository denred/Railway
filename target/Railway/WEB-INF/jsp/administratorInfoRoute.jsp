<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.rout.information"/></title>
    <link rel="icon" type="image/x-icon" href="../../img/icons8-high-speed-train-32.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&family=Poppins:wght@600;700&display=swap"
            rel="stylesheet"/>
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Libraries Stylesheet -->
    <link href="../../lib/animate/animate.min.css" rel="stylesheet" />
    <link href="../../lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet" />
    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet" />
    <!-- Template Stylesheet -->
    <link href="../../css/style.css" rel="stylesheet" />
</head>

<body>
<mrt:navigation/>

<div class="d-flex justify-content-end">
    <div class="h5 p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>
<h3 style="text-align: center;">
    <fmt:message key="admin.rout.list"/>
</h3>


<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 20%"><fmt:message key="rout.name"/></th>
                <th style="width: 5%"><fmt:message key="rout.number"/></th>
                <th style="width: 12%"><fmt:message key="train.number"/></th>
                <th style="width: 10%"><fmt:message key="route"/></th>
                <th style="width: 20%"><fmt:message key="admin.editInformation"/></th>
                <th style="width: 10%"><fmt:message key="delete"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="routeDto" items="${routeDto_list}" varStatus="i">
                <tr>
                    <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>${routeDto.routName}</td>
                    <td>${routeDto.routNumber}</td>
                    <td>${routeDto.trainNumber}</td>
                    <td>
                        <form action="administrator_details_set_rout" method="GET">
                            <input type="hidden" name="routs_id" value="${routeDto.routsId}">
                            <input type="submit" class="btn btn-info" name="details"
                                   value="<fmt:message key="admin.details"/>">
                        </form>
                    </td>
                    <td>
                        <form action="administrator_edit_info_rout" method="GET">
                            <input type="hidden" name="routs_id" value="${routeDto.routsId}">
                            <input type="submit" class="btn btn-info" name="edit_info_rout"
                                   value="<fmt:message key="admin.editInformation"/>">
                        </form>
                    </td>
                    <td>
                        <form action="rout_delete" method="POST">
                            <input type="hidden" name="routs_id" value="${routeDto.routsId}">
                            <input type="submit" class="btn btn-danger" name="remove_rout"
                                   value="<fmt:message key="admin.remove"/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="d-flex justify-content-center">
        <%--For displaying Previous link except for the 1st page --%>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <c:if test="${currentPage != 1}">
                        <a class="page-link" href="administrator_info_route?page=${currentPage - 1}"
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                    </c:if>
                </li>
                <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active" aria-current="page"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="administrator_info_route?page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="administrator_info_route?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<form action="administrator_add_rout" method="GET" id="2">
    <input type="submit" class="btn btn-success ml-2" name="add_rout" value="<fmt:message key="admin.addRout"/>">
</form>
</body>
</html>