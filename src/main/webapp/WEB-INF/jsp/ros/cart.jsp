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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
<link rel="icon" href="data:;base64,=">
<script src="/js/ros.js"></script>
<title>ROS cart</title>
</head>
<body>
    <div class="container-fluid">
        <h1 class="text-center m-5">ROS cart</h1>
        <nav class="navbar navbar-dark bg-primary">
            <span class="p-2">order ${ordering.id} ${ordering.name}</span>
            <div>            
                <a href="/ros/" class="btn btn-primary">
                    <i class="bi bi-box-arrow-up-left"></i>
                </a>               
                <a href="/ros/checkout" class="btn btn-primary">
                    <i class="bi bi-bag-check"></i>
                </a>
            </div>
        </nav>
        
        <table class="table table-hover">
            <c:forEach var="item" items="${menuItems}">
                <c:if test="${item.quantity > 0}">
                    <tr>
                        <td>${item.name}</td>
                        <td id="qty-${item.id}">${item.quantity}</td>
                        <td>
                            <button class="btn btn-primary" onclick="increase(${item.id})">
                                <i class="bi-plus"></i>
                            </button>
                            <button class="btn btn-primary" onclick="decrease(${item.id})">
                                <i class="bi-dash"></i>
                            </button>
                            <button class="btn btn-primary" onclick="remove(${item.id})">
                                <i class="bi-trash"></i>
                            </button>
                        </td>
                        <td id="tot-${item.id}" class="text-end">
                            <fmt:formatNumber type="currency" maxFractionDigits="2"
                                minFractionDigits="2" value="${item.price * item.quantity}" />
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            <tr>
                <td class="w-50"></td>
                <td id="counter">${ordering.counter}</td>
                <td>
                    <button class="btn btn-primary">
                        <i class="bi-trash"></i>
                    </button>
                </td>
                <td id="total" class="text-end">
                    <fmt:formatNumber type="currency" maxFractionDigits="2" minFractionDigits="2" value="${ordering.total}" />
                </td>
            </tr>

        </table>
    </div>
</body>
</html>