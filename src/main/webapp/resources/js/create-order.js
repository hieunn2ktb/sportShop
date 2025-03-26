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

    // Hàm tính tổng tiền
    function updateTotalPrice() {
        let total = 0;
        $("#orderDetailsTable tr").each(function () {
            let price = parseFloat($(this).find(".price").val()) || 0;
            total += price;
        });
        $("#totalPrice").val(total.toFixed(2)); // Gán tổng tiền vào input ẩn
    }

    // Tự động cập nhật giá khi chọn sản phẩm
    $(document).on("change", ".productSelect", function () {
        let row = $(this).closest("tr");
        let unitPrice = $(this).find(":selected").data("price") || 0;
        let quantity = row.find(".quantity").val();
        let totalPrice = (unitPrice * quantity).toFixed(2);

        row.find(".price").val(totalPrice);
        updateTotalPrice(); // Cập nhật tổng tiền
    });

    // Cập nhật giá khi thay đổi số lượng
    $(document).on("input", ".quantity", function () {
        let row = $(this).closest("tr");
        let unitPrice = row.find(".productSelect option:selected").data("price") || 0;
        let quantity = $(this).val();
        let totalPrice = (unitPrice * quantity).toFixed(2);

        row.find(".price").val(totalPrice);
        updateTotalPrice(); // Cập nhật tổng tiền
    });

    // Xoá dòng OrderDetail và cập nhật tổng tiền
    $(document).on("click", ".removeRow", function () {
        $(this).closest("tr").remove();
        updateTotalPrice(); // Cập nhật tổng tiền
    });
});
