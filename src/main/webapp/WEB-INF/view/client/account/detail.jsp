<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý tài khoản</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome (Biểu tượng đẹp hơn) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

    <!-- Custom CSS -->
    <style>
        body {
            background-color: #f8f9fa;
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
        .container {
            max-width: 600px;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card">
        <div class="card-header text-center">
            <h4><i class="fa fa-user"></i> Thông Tin Tài Khoản</h4>
        </div>
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <img class="card-img-top" src="/images/avatar/${user.avatar}"
                     alt="Card image cap">
                <li class="list-group-item"><strong>Email:</strong> ${user.email}</li>
                <li class="list-group-item"><strong>Họ tên:</strong> ${user.fullName}</li>
                <li class="list-group-item"><strong>Số điện thoại:</strong> ${user.phone}</li>
                <li class="list-group-item"><strong>Địa chỉ:</strong> ${user.address}</li>
            </ul>
        </div>

    </div>

    <!-- Các nút hành động -->
    <div class="mt-4">
        <a href="/account/edit" class="btn btn-warning btn-custom"><i class="fa fa-edit"></i> Cập Nhật Thông Tin</a>
    </div>
    <div class="mt-2">
        <a href="/account/change-password" class="btn btn-primary btn-custom"><i class="fa fa-key"></i> Đổi Mật Khẩu</a>
    </div>
    <div class="mt-2">
        <a href="/account/lock" class="btn btn-danger btn-custom" onclick="return confirm('Bạn có chắc chắn muốn khóa tài khoản không?');">
            <i class="fa fa-lock"></i> Khóa Tài Khoản
        </a>
    </div>
</div>

<!-- Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>
