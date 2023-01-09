<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.car"/></title>
    <link rel="icon" type="image/x-icon" href="../../img/icons8-high-speed-train-32.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&family=Poppins:wght@600;700&display=swap"
          rel="stylesheet"/>
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet"/>
    <!-- Libraries Stylesheet -->
    <link href="../../lib/animate/animate.min.css" rel="stylesheet"/>
    <link href="../../lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet"/>
    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Template Stylesheet -->
    <link href="../../css/style.css" rel="stylesheet"/>
</head>
<body>
<mrt:navigation/>
<div class="d-flex justify-content-end">
    <div class="h5 p-2">
        <fmt:message key="enterRole"/>
        <mrt:role role="${user.role}"/>
    </div>
</div>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th><fmt:message key="train.number"/></th>
                <th><fmt:message key="car.type"/></th>
                <th><fmt:message key="car.number"/></th>
                <th><fmt:message key="car.seats"/></th>
                <th><fmt:message key="admin.editInformation"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <tr>
                <form action="administrator_edit_info_car" method="POST">
                    <input type="hidden" name="car_id" value="${current_car.carriageId}">
                    <td><select class="btn btn-info dropdown-toggle" name="train_id">
                        <option value="TRAIN_NOT_SELECTED"><fmt:message key="in.the.depot"/></option>
                        <c:set var="train_id" value="${current_car.trainId}"/>
                        <c:forEach items="${trainList}" var="train">
                            <option
                                    <c:choose>
                                        <c:when test="${train.id eq train_id}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${train.id}"><c:out value="${train.number}"/>
                            </option>
                        </c:forEach>
                    </select>
                    </td>
                    <td><select class="btn btn-info dropdown-toggle" name="car_type">
                        <c:set var="train_id" value="${current_car.type}"/>
                        <c:forEach items="${carTypeList}" var="car_type">
                            <option
                                    <c:choose>
                                        <c:when test="${car_type eq current_car.type}">
                                            selected
                                        </c:when>
                                    </c:choose>
                                    value="${car_type}"><fmt:message key="${car_type}"/>
                            </option>
                        </c:forEach>
                    </select>
                    </td>
                    <td><input name="car_number" class="form-control" value="${current_car.number}"></td>
                    <td><input name="seats" class="form-control" value="${countSeat}"></td>
                    <td>
                        <input type="hidden" name="car_id" value="${current_car.carriageId}">
                        <input type="submit" class="btn btn-success" name="save_edit_information"
                               value="<fmt:message key="admin.saveInformation"/>">
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
    <form action="administrator_info_carriage" method="GET">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
</body>
</html>
