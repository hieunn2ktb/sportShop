<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Update User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            const orgImage = "${newUser.avatar}";
            if (orgImage) {
                const urlImage = "/images/avatar/" + orgImage;
                $("#avatarPreview").attr("src", urlImage).show();
            }

            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL).show();
            });
        });
    </script>
</head>

<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-lg">
                <div class="card-header bg-primary text-white text-center">
                    <h3 class="mb-0">Cập nhật thông tin</h3>
                </div>
                <div class="card-body">
                    <form:form method="post" action="/account/update" modelAttribute="newUser" enctype="multipart/form-data">
                        <div class="mb-3" style="display: none;">
                            <label class="form-label">Id:</label>
                            <form:input type="text" class="form-control" path="id" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <form:input type="email" class="form-control" path="email" disabled="true" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại:</label>
                            <form:input type="text" class="form-control" path="phone" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Họ và tên:</label>
                            <form:input type="text" class="form-control" path="fullName" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Địa chỉ:</label>
                            <form:input type="text" class="form-control" path="address" />
                        </div>

                        <div class="mb-3">
                            <label for="avatarFile" class="form-label">Hình ảnh:</label>
                            <input class="form-control" type="file" id="avatarFile" accept=".png, .jpg, .jpeg" name="accountFile" />
                        </div>

                        <div class="text-center mb-3">
                            <img id="avatarPreview" style="max-height: 250px; display: none;" class="rounded-circle shadow" alt="avatar preview" />
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-success">
                                <i class="fa-solid fa-save"></i> Cập nhật
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
