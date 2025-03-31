document.getElementById("btnFilter").addEventListener("click", function () {
    let selectedFactories = [];
    let selectedPrices = [];

    // Lấy các giá trị đã chọn của hãng sản xuất
    document.querySelectorAll("#factoryFilter .form-check-input:checked").forEach(input => {
        selectedFactories.push(input.value);
    });

    // Lấy các giá trị đã chọn của mức giá
    document.querySelectorAll("#priceFilter .form-check-input:checked").forEach(input => {
        selectedPrices.push(input.value);
    });

    if (selectedFactories.length === 0 && selectedPrices.length === 0) {
        window.location.href = "/products";
        return;
    }
    // Tạo URL với các tham số
    let url = new URL(window.location.href);

    if (selectedFactories.length > 0) {
        url.searchParams.set("factory", selectedFactories.join(","));
    } else {
        url.searchParams.delete("factory");
    }

    if (selectedPrices.length > 0) {
        url.searchParams.set("price", selectedPrices.join(","));
    } else {
        url.searchParams.delete("price");
    }

    // Chuyển hướng đến URL mới
    window.location.href = url.toString();
});