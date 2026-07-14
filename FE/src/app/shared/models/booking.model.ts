export type VehicleType = '4-cho' | '7-cho' | 'hop-dong';

export const VEHICLE_OPTIONS: { value: VehicleType; label: string }[] = [
  { value: '4-cho', label: '4 chỗ (Tiết kiệm)' },
  { value: '7-cho', label: '7 chỗ (Rộng rãi)' },
  { value: 'hop-dong', label: 'Xe hợp đồng / Du lịch' },
];

/** Matches FR-02 minimal booking fields. */
export interface BookingRequest {
  customerName: string;
  phone: string;
  pickup: string;
  dropoff: string;
  pickupTime: string;
  vehicleType: VehicleType;
  note?: string;
}

/** Server response after a booking is created (status NEW). */
export interface BookingResult {
  code: string;
  status: 'NEW';
}
