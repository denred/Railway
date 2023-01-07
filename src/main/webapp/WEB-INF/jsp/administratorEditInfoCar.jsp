<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mrt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.car"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style>
        body {
            background-color: #f5f5f5;
        }

        table {
            table-layout: fixed;
            width: auto;
            height: auto;
            text-align: center;
        }

        tr {
            width: auto;
            height: auto;
            text-align: center;

        }

        td {
            width: auto;
            text-align: center;

        }
    </style>
</head>
<body>
<mrt:navigation/>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<div class="h5" align="right">
    <fmt:message key="enterRole"></fmt:message>
    <mrt:role role="${user.role}"></mrt:role>
</div>
<table class="table table-bordered text-center" border="1" style="width: auto">
    <thead class="thead-light text-center">
    <tr>
        <th><fmt:message key="train.number"/></th>
        <th><fmt:message key="car.type"/></th>
        <th><fmt:message key="car.number"/></th>
        <th><fmt:message key="car.seats"/></th>
        <th><fmt:message key="admin.editInformation"/></th>
    </tr>
    </thead>
    <tbody>
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
<form action="administrator_account" method="GET">
    <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
</form>
</body>
</html>
