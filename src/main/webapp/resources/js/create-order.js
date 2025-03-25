$(document).ready(function () {
    // Lấy danh sách sản phẩm từ JSP
    let products = JSON.parse($("#productData").val());

    $("#addRow").click(function () {
        let index = $("#orderDetailsTable tr").length;

        // Tạo danh sách <option>
        let productOptions = '<option value="">Select Product</option>';
        products.forEach(product => {
            productOptions += `<option value="${product.id}" data-price="${product.price}">${product.name}</option>`;
        });

        let newRow = `
            <tr>
                <td>
                    <select name="orderDetails[${index}].product.id" class="form-control productSelect">
                        ${productOptions}
                    </select>
                </td>
                <td>
                    <input type="number" name="orderDetails[${index}].quantity" class="form-control quantity" min="1" value="1"/>
                </td>
                <td>
                    <input type="number" name="orderDetails[${index}].price" class="form-control price" step="0.01" readonly/>
                </td>
                <td>
                    <button type="button" class="btn btn-danger removeRow">Remove</button>
                </td>
            </tr>`;
        $("#orderDetailsTable").append(newRow);
    });

    // Tự động cập nhật giá khi chọn sản phẩm
    $(document).on("change", ".productSelect", function () {
        let unitPrice = $(this).find(":selected").data("price") || 0;
        let quantity = $(this).closest("tr").find(".quantity").val();
        let totalPrice = (unitPrice * quantity).toFixed(2);

        $(this).closest("tr").find(".price").val(totalPrice);
    });

    // Cập nhật giá khi thay đổi số lượng
    $(document).on("input", ".quantity", function () {
        let row = $(this).closest("tr");
        let unitPrice = row.find(".productSelect option:selected").data("price") || 0;
        let quantity = $(this).val();
        let totalPrice = (unitPrice * quantity).toFixed(2);

        row.find(".price").val(totalPrice);
    });

    // Xoá dòng OrderDetail
    $(document).on("click", ".removeRow", function () {
        $(this).closest("tr").remove();
    });
});
