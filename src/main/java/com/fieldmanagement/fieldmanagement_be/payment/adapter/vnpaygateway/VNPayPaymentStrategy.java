package com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpaygateway;

import com.fieldmanagement.fieldmanagement_be.booking.domain.dto.BookingDTO;
import com.fieldmanagement.fieldmanagement_be.booking.domain.model.BookingStatusEnum;
import com.fieldmanagement.fieldmanagement_be.booking.domain.port.BookingQueryPort;
import com.fieldmanagement.fieldmanagement_be.common.util.SecurityUtils;
import com.fieldmanagement.fieldmanagement_be.field.exception.BookingNotFoundException;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpay.VNPayProperties;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.vnpay.VNpayUtils;
import com.fieldmanagement.fieldmanagement_be.payment.adapter.web.dto.request.CreatePaymentRequest;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.Payment;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentMethodEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.model.PaymentTypeEnum;
import com.fieldmanagement.fieldmanagement_be.payment.domain.port.PaymentRepository;
import com.fieldmanagement.fieldmanagement_be.user.exception.UserDoesNotHavePermission;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Component("vnpay")
@RequiredArgsConstructor
public class VNPayPaymentStrategy implements PaymentStrategy {
    private final VNPayProperties properties;
    private final BookingQueryPort bookingQueryPort;
    private final PaymentRepository paymentRepository;
    private final VNpayUtils vnPayUtils;

    @Override
    public String processPayment(CreatePaymentRequest request, HttpServletRequest httpRequest)
            throws UnsupportedEncodingException {
        // 1. Validate và lấy thông tin booking
        BookingDTO bookingDTO = bookingQueryPort.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException("Không tìm thấy đơn đặt sân"));

        // 3. Chuẩn bị tham số bắt buộc
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderType = "other";

        long amount = bookingDTO.getHourlyRate().getPrice() * 100;
        String vnp_TxnRef = vnPayUtils.getRandomNumber(8);
        String vnp_IpAddr = vnPayUtils.getIpAddress(httpRequest);
        String vnp_TmnCode = properties.getTmnCode();

        String returnURL = properties.getReturnUrl()
                + "?bookingId=" + bookingDTO.getId()
                + "&paymentType=" + request.getPaymentType().name();

        // 4. Tạo map tham số (sử dụng LinkedHashMap để giữ thứ tự)
        Map<String, String> vnp_Params = new LinkedHashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount)); // Số tiền đã định dạng
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan hoa don: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnURL);
        vnp_Params.put("vnp_IpAddr", "0:0:0:0:0:0:0:1".equals(vnp_IpAddr) ? "127.0.0.1" : vnp_IpAddr);// Lấy IP thực tế

        // 5. Định dạng ngày tháng với timezone chính xác (GMT+7)
        TimeZone vnTimeZone = TimeZone.getTimeZone("GMT+7");
        Calendar calendar = Calendar.getInstance(vnTimeZone);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(vnTimeZone);

        String vnp_CreateDate = dateFormat.format(calendar.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = dateFormat.format(calendar.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        // 8. Tạo chữ ký bảo mật
        String vnp_SecureHash = vnPayUtils.hmacSHA512(properties.getHashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        bookingDTO.setStatus(BookingStatusEnum.AWAITING_PAYMENT);
        bookingQueryPort.save(bookingDTO);
        // 9. Trả về URL hoàn chỉnh
        return properties.getPayUrl() + "?" + query.toString();
    }

    @Transactional
    @Override
    public Payment processCallback(Map<String, String> params) {
        String bookingId = params.get("bookingId");
        String paymentType = params.get("paymentType");

        String vnpResponseCode = params.get("vnp_ResponseCode");
        String vnpTransactionStatus = params.get("vnp_TransactionStatus");
        String vnpTxnRef = params.get("vnp_TxnRef");
        String vnpOrderInfo = params.get("vnp_OrderInfo");
        String vnpPayDateStr = params.get("vnp_PayDate");
        Long amount = Long.parseLong(params.get("vnp_Amount")) / 100;

        if (!"00".equals(vnpResponseCode) || !"00".equals(vnpTransactionStatus)) {
            throw new RuntimeException("Giao dịch thất bại hoặc chưa hoàn tất");
        }

        BookingDTO bookingDTO = bookingQueryPort.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Không tìm thấy đơn đặt sân"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse(vnpPayDateStr, formatter);

        ZoneOffset zoneOffset = ZoneOffset.ofHours(7);
        OffsetDateTime offsetDateTime = localDateTime.atOffset(zoneOffset);

        PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.valueOf(paymentType);
        if(paymentTypeEnum == PaymentTypeEnum.DEPOSIT)
            bookingDTO.setStatus(BookingStatusEnum.PAID_DEPOSIT);
        else if(paymentTypeEnum == PaymentTypeEnum.PAY) bookingDTO.setStatus(BookingStatusEnum.PAID);

        bookingQueryPort.save(bookingDTO);
        Payment payment = new Payment();
        payment.setBooking(bookingDTO);
        payment.setAmount(amount);
        payment.setPaymentType(paymentTypeEnum);
        payment.setNote(vnpOrderInfo);
        payment.setVnpTxnRef(vnpTxnRef);
        payment.setPaymentTime(offsetDateTime);
        payment.setPaymentMethod(PaymentMethodEnum.VNPAY);

        return paymentRepository.save(payment);
    }

    @Override
    public void processRefund(CreatePaymentRequest request) {
        return;
    }
}
