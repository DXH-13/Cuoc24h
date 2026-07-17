package com.cuoc24h.api.driver.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Public driver registration request (FR-03). */
public record DriverRegistrationRequest(
        @NotBlank(message = "Vui long nhap ho ten")
        @Size(max = 120, message = "Ho ten toi da 120 ky tu")
        String fullName,

        @NotBlank(message = "Vui long nhap so dien thoai")
        @Pattern(regexp = "^0\\d{8,10}$", message = "So dien thoai khong hop le")
        String phone,

        @NotBlank(message = "Vui long nhap khu vuc hoat dong")
        @Size(max = 255, message = "Khu vuc toi da 255 ky tu")
        String operatingArea,

        @NotBlank(message = "Vui long chon loai xe")
        @Size(max = 50, message = "Loai xe khong hop le")
        String vehicleType,

        @NotBlank(message = "Vui long nhap bien so xe")
        @Size(max = 30, message = "Bien so toi da 30 ky tu")
        String licensePlate,

        @NotNull(message = "Vui long nhap so ghe")
        @Min(value = 2, message = "So ghe toi thieu la 2")
        @Max(value = 45, message = "So ghe toi da la 45")
        Integer seatCount,

        @NotBlank(message = "Vui long chon hinh thuc chay xe")
        @Size(max = 80, message = "Hinh thuc chay xe khong hop le")
        String serviceType,

        @Size(max = 2000, message = "Ghi chu qua dai")
        String note) {
}
