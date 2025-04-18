

$(document).ready(function() {
    // Đặt tất cả các thông báo chưa đọc là in đậm
    $(".notification.unread").css("font-weight", "bold");

    // Xử lý sự kiện khi người dùng nhấp vào "Đọc"
    $(".notification a").click(function(e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
        var row = $(this).closest("tr"); // Tìm dòng chứa thông báo

        // Toggle lớp "unread" khi nhấp vào liên kết "Đọc"
        row.toggleClass("unread");

        // Cập nhật lại trạng thái của văn bản: nếu chưa đọc, in đậm, nếu đã đọc, bỏ in đậm
        if (row.hasClass("unread")) {
            row.css("font-weight", "bold");
        } else {
            row.css("font-weight", "normal");
        }
    });
});