<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đổi Mật Khẩu</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 500px;
        }
        .card {
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #007bff;
            color: white;
            font-size: 20px;
        }
        .btn-custom {
            width: 100%;
            border-radius: 8px;
            padding: 10px;
            font-size: 16px;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card">
        <div class="card-header text-center">
            <h4><i class="fa fa-key"></i> Đổi Mật Khẩu</h4>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <form action="/account/update-password" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="mb-3">
                    <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                    <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label">Mật khẩu mới</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="btn btn-primary btn-custom"><i class="fa fa-save"></i> Lưu Mật Khẩu</button>
            </form>
        </div>
    </div>

    <!-- Nút quay lại -->
    <div class="mt-4 text-center">
        <a href="/" class="btn btn-secondary btn-custom">
            <i class="fa fa-arrow-left"></i> Quay lại
        </a>
    </div>
</div>

<!-- Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
