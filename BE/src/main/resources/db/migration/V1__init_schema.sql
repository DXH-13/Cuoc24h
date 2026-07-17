-- Cuoc24h MVP schema (MySQL). See docs/05-database-design.md.

CREATE TABLE admin_users (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    username      VARCHAR(80)  NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    display_name  VARCHAR(120) NOT NULL,
    role          VARCHAR(30)  NOT NULL DEFAULT 'ADMIN',
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_admin_users_username (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE drivers (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    full_name      VARCHAR(120) NOT NULL,
    phone          VARCHAR(30)  NOT NULL,
    operating_area VARCHAR(255) NOT NULL,
    vehicle_type   VARCHAR(50)  NOT NULL,
    license_plate  VARCHAR(30)  NOT NULL,
    seat_count     INT          NOT NULL,
    service_type   VARCHAR(80)  NOT NULL,
    note           TEXT         NULL,
    admin_note     TEXT         NULL,
    status         VARCHAR(30)  NOT NULL DEFAULT 'PENDING',
    created_at     DATETIME     NOT NULL,
    updated_at     DATETIME     NOT NULL,
    PRIMARY KEY (id),
    KEY idx_drivers_status_created (status, created_at),
    KEY idx_drivers_phone (phone)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE bookings (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    public_code        VARCHAR(32)  NOT NULL,
    customer_name      VARCHAR(120) NOT NULL,
    customer_phone     VARCHAR(30)  NOT NULL,
    pickup_address     VARCHAR(255) NOT NULL,
    dropoff_address    VARCHAR(255) NOT NULL,
    pickup_time        DATETIME     NULL,
    vehicle_type       VARCHAR(50)  NULL,
    note               TEXT         NULL,
    internal_note      TEXT         NULL,
    status             VARCHAR(30)  NOT NULL DEFAULT 'NEW',
    assigned_driver_id BIGINT       NULL,
    created_at         DATETIME     NOT NULL,
    updated_at         DATETIME     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_bookings_public_code (public_code),
    KEY idx_bookings_status_created (status, created_at),
    KEY idx_bookings_customer_phone (customer_phone),
    CONSTRAINT fk_bookings_driver FOREIGN KEY (assigned_driver_id) REFERENCES drivers (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE booking_status_history (
    id                  BIGINT      NOT NULL AUTO_INCREMENT,
    booking_id          BIGINT      NOT NULL,
    old_status          VARCHAR(30) NULL,
    new_status          VARCHAR(30) NOT NULL,
    note                TEXT        NULL,
    changed_by_admin_id BIGINT      NULL,
    created_at          DATETIME    NOT NULL,
    PRIMARY KEY (id),
    KEY idx_bsh_booking (booking_id),
    CONSTRAINT fk_bsh_booking FOREIGN KEY (booking_id) REFERENCES bookings (id),
    CONSTRAINT fk_bsh_admin FOREIGN KEY (changed_by_admin_id) REFERENCES admin_users (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE notification_logs (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    booking_id      BIGINT       NOT NULL,
    channel         VARCHAR(30)  NOT NULL DEFAULT 'ZALO',
    recipient       VARCHAR(255) NULL,
    message_preview TEXT         NULL,
    status          VARCHAR(30)  NOT NULL DEFAULT 'PENDING',
    error_message   TEXT         NULL,
    sent_at         DATETIME     NULL,
    created_at      DATETIME     NOT NULL,
    PRIMARY KEY (id),
    KEY idx_notif_booking_channel (booking_id, channel),
    CONSTRAINT fk_notif_booking FOREIGN KEY (booking_id) REFERENCES bookings (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
