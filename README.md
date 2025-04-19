# ⚽ Field Management System

## 📝 Giới thiệu
Field Management là một hệ thống quản lý và cho thuê sân bóng chuyên nghiệp. Hệ thống cho phép người dùng tìm kiếm, đặt sân trực tuyến, đồng thời hỗ trợ quản lý vận hành sân bóng cho chủ sân và nhân viên. Tích hợp cổng thanh toán VNPay, người dùng có thể thanh toán nhanh chóng, an toàn và tiện lợi.

---
## 🚀 Tính năng chính
### Đối với người dùng (khách đặt sân)
- Xem danh sách cụm sân, sân con và thông tin chi tiết từng sân.
- Xem lịch trống của sân và đặt sân theo khung giờ cụ thể.
- Thanh toán cọc hoặc toàn bộ phí thuê sân qua VNPay.
- Hủy đặt sân (theo chính sách hoàn tiền của từng cụm sân).
- Nhận thông báo xác nhận và cập nhật trạng thái đơn đặt.
### Đối với chủ sân (Manager)
- Quản lý cụm sân, sân con, khung giờ hoạt động.
- Thiết lập giá thuê, chính sách cọc và hoàn tiền.
- Xem thống kê doanh thu, lịch sử đặt sân.
- Quản lý nhân viên hỗ trợ vận hành sân.

### Đối với nhân viên (Staff)
- Xác nhận đặt sân và hỗ trợ khách tại sân.
- Ghi nhận thanh toán tại sân (nếu có).
- Hủy/hoàn tiền cho khách theo yêu cầu.

### Đối với quản trị viên hệ thống (Admin)
- Quản lý người dùng hệ thống.
- Quản lý các cụm sân được đăng ký trên hệ thống.
- Kiểm duyệt và phân quyền cho chủ sân mới.
---
## 🏗️ Kiến trúc hệ thống

- **Backend:** Spring Boot (REST API, Clean Architecture)
- **Frontend:** 
- **Cơ sở dữ liệu:** MariaDB
- **Thanh toán:** VNPay, Tiền mặt
- **Realtime Data:** WebSocket (thông báo)

---
## 🗂️ Cấu trúc dự án

```plaintext
fieldManagement/
├── fieldmanagement_be/   # Backend chính (Spring Boot)
│   ├── common/               # Các class dùng chung (exception, validator, annotation...)
│   ├── user/             # Module quản lý người dùng (Clean Architecture)
│   ├── field/            # Module quản lý sân bóng
│   ├── booking/          # Module đặt sân
│   ├── payment/          # Tích hợp thanh toán VNPay
│   └── ...
