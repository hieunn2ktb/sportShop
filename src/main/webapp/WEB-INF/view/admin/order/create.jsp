<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Create Product</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            });
        });
    </script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Order</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item"><a href="/admin/order">Order</a></li>
                    <li class="breadcrumb-item active">Create</li>
                </ol>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Create a Order</h3>
                            <hr />
                            <input type="hidden" id="productData" value='${productsJson}' />
                            <form:form method="post" action="/admin/order/create" class="row"
                                       enctype="multipart/form-data" modelAttribute="newOrder">
                                <c:set var="errorReceiverName">
                                    <form:errors path="receiverName" cssClass="invalid-feedback" />
                                </c:set>
                                <c:set var="errorReceiverAddress">
                                    <form:errors path="receiverAddress" cssClass="invalid-feedback" />
                                </c:set>
                                <c:set var="erroRreceiverPhone">
                                    <form:errors path="receiverPhone" cssClass="invalid-feedback" />
                                </c:set>

                                <div class="mb-3 col-12 col-md-6">
                                    <label class="form-label">Receiver Name:</label>
                                    <form:input type="text"
                                                class="form-control ${not empty errorReceiverName ? 'is-invalid' : ''}"
                                                path="receiverName" />
                                        ${errorReceiverName}
                                </div>
                                <div class="mb-3 col-12">
                                    <label class="form-label">Receiver Address:</label>
                                    <form:input type="text"
                                                   class="form-control ${not empty errorReceiverAddress ? 'is-invalid' : ''}"
                                                   path="receiverAddress" />
                                        ${errorReceiverAddress}
                                </div>
                                <div class="mb-3 col-12 col-md-6">
                                    <label class="form-label">Receiver Phone:</label>
                                    <form:input type="text"
                                                class="form-control ${not empty erroRreceiverPhone ? 'is-invalid' : ''}"
                                                path="receiverPhone" />
                                        ${erroRreceiverPhone}
                                </div>
                                <div class="mb-3 col-12">
                                    <h5>Order Details:</h5>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>Product</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody id="orderDetailsTable">
                                        <c:forEach items="${newOrder.orderDetails}" var="orderDetail" varStatus="status">
                                            <tr>
                                                <td>
                                                    <form:select path="orderDetails[${status.index}].product.id"
                                                                 class="form-control">
                                                        <option value="">Select Product</option>
                                                        <c:forEach items="${products}" var="product">
                                                            <option value="${product.id}">${product.name}</option>
                                                        </c:forEach>
                                                    </form:select>
                                                </td>
                                                <td>
                                                    <form:input type="number" path="orderDetails[${status.index}].quantity"
                                                                class="form-control" min="1"/>
                                                </td>
                                                <td>
                                                    <form:input type="number" path="orderDetails[${status.index}].price"
                                                                class="form-control" step="0.01"/>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-danger removeRow">Remove</button>
                                                </td>
                                            </tr>

                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <button type="button" id="addRow" class="btn btn-primary">Add Product</button>
                                </div>
                                <div class="col-12 mb-5">
                                    <input type="hidden" id="totalPrice" name="totalPrice" value="0" />
                                    <button type="submit" class="btn btn-primary">Create</button>
                                </div>
                            </form:form>
                        </div>

                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script>
    let products = [];
    <c:forEach items="${products}" var="product">
    products.push({ id: ${product.id}, name: "${product.name}" });
    </c:forEach>
</script>
<script src="/js/create-order.js"></script>
</body>

</html>