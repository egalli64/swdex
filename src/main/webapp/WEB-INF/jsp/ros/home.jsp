<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link rel="icon" href="data:;base64,=">
<title>Restaurant Ordering System</title>
</head>
<body>
    <div class="container-fluid">
        <h1 class="text-center m-5">A Very Simple Restaurant Ordering System</h1>

        <c:forEach var="category" items="${categories}">
            <h2>${category.name}</h2>
            <c:if test="${not empty category.description}">
                <h3 class="text-end">${category.description}</h3>
            </c:if>
            <table class="table table-hover">
                <c:forEach var="item" items="${menuItems}">
                    <c:if test="${category.id eq item.category}">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
                            <td class="text-end"><fmt:formatNumber type="currency" maxFractionDigits="2"
                                    minFractionDigits="2" value="${item.price}" /></td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:forEach>
    </div>
</body>
</html>