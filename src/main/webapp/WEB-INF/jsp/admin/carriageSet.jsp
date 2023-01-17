<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="admin.edit.info.car"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

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
                <form action="controller?action=set_carriage" method="POST">
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
                        <input type="hidden" name="routeDto" value="${car_list}">
                        <input type="hidden" name="trainFilter" value="${trainFilter}">
                        <input type="hidden" name="carriageTypeFilter" value="${carriageTypeFilter}">
                        <input type="submit" class="btn btn-success" name="save_edit_information"
                               value="<fmt:message key="admin.saveInformation"/>">
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
    <form action="controller?action=carriages" method="POST">
        <input type="submit" class="btn btn-primary" value="<fmt:message key="back"/>">
    </form>
</div>
</body>
</html>
