function validateForm() {
    let result = true;

    var size = document.getElementById("size").value;
    if (size === "") {
        alert("Vui lòng chọn size!");
        result = false;
    }

    var brand = document.getElementById("brand").value;
    if (brand === "") {
        alert("Vui lòng chọn thương hiệu!");
        result = false;
    }

    var category = document.getElementById("category").value;
    if (category === "") {
        alert("Vui lòng chọn danh mục sản phẩm!");
        result = false;
    }

    return result;
}
