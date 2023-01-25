<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <title><fmt:message key="admin.car.information"/></title>
    <jsp:include page="/WEB-INF/templates/_head.jsp"/>
</head>
<body>
<mrt:navigation/>
<jsp:include page="/WEB-INF/templates/_role.jsp"/>

<h3 style="text-align: center;">
    <fmt:message key="admin.carriage.list"/>
</h3>
<div class="container mt-4">
    <div class="d-flex justify-content-center">
        <form action="controller?action=carriages" method="POST">
            <div class="row">
                <div class="col-4">
                    <input class="form-control" name="trainFilter" type="text"
                           placeholder="<fmt:message key="filter.train"/>" value="${trainFilter}">
                </div>
                <div class="col-4">
                    <select class="btn btn-info dropdown-toggle" name="carriageTypeFilter">
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
                </div>
                <div class="col-2">
                    <input type="hidden" name="routeDto" value="${car_list}">
                    <input type="submit" class="btn btn-info" name="filter"
                           value="<fmt:message key="route.filter"/>">
                </div>
            </div>
        </form>
    </div>
    <div class="d-flex justify-content-center">
        <table class="table table-bordered table-hover caption-top" style="width: 1000px;">
            <thead class="thead-light text-center">
            <tr>
                <th style="width: 1%"><fmt:message key="order"/></th>
                <th style="width: 20%"><fmt:message key="train.number"/></th>
                <th style="width: 20%"><fmt:message key="car.type"/></th>
                <th style="width: 10%"><fmt:message key="car.number"/></th>
                <th style="width: 10%"><fmt:message key="car.seats"/></th>
                <th style="width: 10%"><fmt:message key="car.price"/></th>
                <th style="width: 15%"><fmt:message key="edit"/></th>
                <th style="width: 15%"><fmt:message key="delete"/></th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:forEach var="carriage" items="${car_list}" varStatus="i">
                <tr>
                    <td>${i.index + recordsPerPage * (currentPage - 1) + 1}</td>
                    <td>${carriage.trainNumber}</td>
                    <td><fmt:message key="${carriage.carriageType}"/></td>
                    <td>${carriage.carNumber}</td>
                    <td>${carriage.seats}</td>
                    <td>${carriage.price}</td>
                    <td>
                        <form action="controller?action=set_carriage" method="POST">
                            <input type="hidden" name="car_id" value="${carriage.carId}">
                            <input type="hidden" name="trainFilter" value="${trainFilter}">
                            <input type="hidden" name="carriageTypeFilter" value="${carriageTypeFilter}">
                            <input type="submit" class="btn btn-info" name="edit_info_car"
                                   value="<fmt:message key="admin.editInformation"/>">
                        </form>
                    </td>
                    <td>
                        <form action="controller?action=remove_carriage" method="POST">
                            <input type="hidden" name="car_id" value="${carriage.carId}">
                            <input type="submit" class="btn btn-danger" name="remove_car"
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
                        <a class="page-link" href="controller?action=carriages&page=${currentPage - 1}"
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
                                                     href="controller?action=carriages&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=carriages&page=${currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <form action="controller?action=set_carriage" method="POST">
        <input type="submit" class="btn btn-success" name="add_car" value="<fmt:message key="admin.addCar"/>">
    </form>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"/>
</body>
</html>