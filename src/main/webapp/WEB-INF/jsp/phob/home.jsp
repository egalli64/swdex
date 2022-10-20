<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
<link rel="icon" href="data:;base64,=">
<title>Phone Book</title>
</head>
<body>
    <div class="container-fluid">
        <h1 class="text-center m-5">A Very Simple Phone Book</h1>

        <div class="row">
            <div class="col-1"></div>
            <div class="col-6">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Phone</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <c:forEach var="contact" items="${contacts}">
                        <tr>
                            <td>${contact.name}</td>
                            <td>${contact.phone}</td>
                            <td><a href="/phob/delete?id=${contact.id}" class="btn btn-danger"><i class="bi-trash"></i></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col-1"></div>
            <div class="col-3 border border-info p-2">
                <h2 class="text-center">New Contact</h2>
                <form action="/phob/create">
                    <div>
                        <label>Name <input name="name" class="form-control" required>
                        </label>
                    </div>
                    <div>
                        <label>Phone <input name="phone" class="form-control" required>
                        </label>
                    </div>
                    <div class="mt-3">
                        <button class="btn btn-primary">Create</button>
                    </div>
                </form>
            </div>
            <div class="col-1"></div>
        </div>
    </div>
</body>
</html>