<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cập Nhật Thông Tin</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            const orgImage = "${user.avatar}";
            if (orgImage) {
                const urlImage = "/images/avatar/" + orgImage;
                $("#avatarPreview").attr("src", urlImage);
                $("#avatarPreview").css({ "display": "block" });
            }

            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            });
        });
    </script>
    <style>
        body {
            background-color: #f8f9fa;
        }

        .card {
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #28a745;
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
            <h4><i class="fa fa-edit"></i> Cập Nhật Thông Tin</h4>
        </div>
        <div class="card-body">
            <form:form method="post" action="/account/update"
                       modelAttribute="newUser">
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <form:input type="email" class="form-control" path="email"
                                disabled="true" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Phone number:</label>
                    <form:input type="text" class="form-control" path="phone" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Full Name:</label>
                    <form:input type="text" class="form-control" path="fullName" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Address:</label>
                    <form:input type="text" class="form-control" path="address" />
                </div>

                <div class="mb-3 col-12 col-md-6">
                    <label for="avatarFile" class="form-label">Image:</label>
                    <input class="form-control" type="file" id="avatarFile"
                           accept=".png, .jpg, .jpeg" name="accountFile" />
                </div>
                <div class="col-12 mb-3">
                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                         id="avatarPreview" />
                </div>
                <button type="submit" class="btn btn-success btn-custom"><i class="fa fa-save"></i> Lưu Thay
                    Đổi</button>
            </form:form>
        </div>
    </div>
    <div class="mt-4">
        <a href="/account/info" class="btn btn-secondary btn-custom"><i class="fa fa-arrow-left"></i> Quay Lại</a>
    </div>
</div>

<!-- Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>