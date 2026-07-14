export const DRIVER_VEHICLE_OPTIONS = ['4 chỗ', '7 chỗ', '16 chỗ', 'Xe hợp đồng'] as const;
export const DRIVER_WORK_MODES = ['Toàn thời gian', 'Bán thời gian', 'Theo chuyến'] as const;

/** Matches FR-03 minimal driver-registration fields. */
export interface DriverRegistration {
  fullName: string;
  phone: string;
  area: string;
  vehicleType: string;
  plateNumber: string;
  seats: number;
  workMode: string;
  note?: string;
}

/** Server response after a driver profile is created (status PENDING). */
export interface DriverRegistrationResult {
  code: string;
  status: 'PENDING';
}
