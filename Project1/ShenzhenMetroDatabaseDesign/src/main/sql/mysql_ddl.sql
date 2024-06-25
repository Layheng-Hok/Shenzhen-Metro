BEGIN;

DROP TABLE IF EXISTS line_detail CASCADE;
DROP TABLE IF EXISTS landmark_exit_info CASCADE;
DROP TABLE IF EXISTS bus_exit_info CASCADE;
DROP TABLE IF EXISTS ride_by_card_num CASCADE;
DROP TABLE IF EXISTS ride_by_id_num CASCADE;
DROP TABLE IF EXISTS route_pricing CASCADE;
DROP TABLE IF EXISTS bus_info CASCADE;
DROP TABLE IF EXISTS station CASCADE;
DROP TABLE IF EXISTS passenger CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS line CASCADE;
DROP TABLE IF EXISTS landmark_info CASCADE;

COMMIT;

BEGIN;

CREATE TABLE IF NOT EXISTS station
(
    english_name VARCHAR(50) PRIMARY KEY,
    chinese_name VARCHAR(50) NOT NULL,
    district     VARCHAR(50),
    intro        TEXT,
    UNIQUE (chinese_name)
);

CREATE TABLE IF NOT EXISTS bus_info
(
    bus_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bus_line    VARCHAR(50) NOT NULL,
    bus_name    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS bus_exit_info
(
    bus_exit_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    station_name     VARCHAR(50)  NOT NULL,
    exit_gate        VARCHAR(100) NOT NULL,
    bus_info_id      BIGINT       NOT NULL,
    FOREIGN KEY (station_name) REFERENCES station (english_name),
    FOREIGN KEY (bus_info_id) REFERENCES bus_info (bus_info_id)
);

CREATE TABLE IF NOT EXISTS landmark_info
(
    landmark_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    landmark    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS landmark_exit_info
(
    landmark_exit_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    station_name          VARCHAR(50)  NOT NULL,
    exit_gate             VARCHAR(100) NOT NULL,
    landmark_id           BIGINT       NOT NULL,
    FOREIGN KEY (station_name) REFERENCES station (english_name),
    FOREIGN KEY (landmark_id) REFERENCES landmark_info (landmark_id)
);

CREATE TABLE IF NOT EXISTS line
(
    line_name     VARCHAR(5) PRIMARY KEY,
    start_time    TIME NOT NULL,
    end_time      TIME NOT NULL,
    intro         TEXT,
    mileage       REAL,
    color         VARCHAR(5),
    first_opening DATE,
    url           VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS line_detail
(
    line_name     VARCHAR(5),
    station_name  VARCHAR(50),
    station_order INTEGER NOT NULL,
    PRIMARY KEY (line_name, station_name),
    FOREIGN KEY (line_name) REFERENCES line (line_name),
    FOREIGN KEY (station_name) REFERENCES station (english_name)
);

CREATE TABLE IF NOT EXISTS card
(
    code        VARCHAR(10) PRIMARY KEY,
    money       REAL NOT NULL,
    create_time TIMESTAMP(0)
);

CREATE TABLE IF NOT EXISTS passenger
(
    id_num    VARCHAR(20) PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    phone_num BIGINT,
    gender    CHAR,
    district  VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS route_pricing
(
    pricing_id    INTEGER AUTO_INCREMENT PRIMARY KEY,
    start_station VARCHAR(50) NOT NULL,
    end_station   VARCHAR(50) NOT NULL,
    price         INTEGER     NOT NULL,
    FOREIGN KEY (start_station) REFERENCES station (english_name),
    FOREIGN KEY (end_station) REFERENCES station (english_name),
    UNIQUE (start_station, end_station)
);

CREATE TABLE IF NOT EXISTS ride_by_id_num
(
    ride_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_num   VARCHAR(20)  NOT NULL,
    start_time TIMESTAMP(0) NOT NULL,
    end_time   TIMESTAMP(0),
    pricing_id INTEGER,
    FOREIGN KEY (user_num) REFERENCES passenger (id_num),
    FOREIGN KEY (pricing_id) REFERENCES route_pricing (pricing_id),
    UNIQUE (user_num, start_time, end_time, pricing_id)
);

CREATE TABLE IF NOT EXISTS ride_by_card_num
(
    ride_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_num   VARCHAR(10)  NOT NULL,
    start_time TIMESTAMP(0) NOT NULL,
    end_time   TIMESTAMP(0),
    pricing_id INTEGER,
    FOREIGN KEY (user_num) REFERENCES card (code),
    FOREIGN KEY (pricing_id) REFERENCES route_pricing (pricing_id),
    UNIQUE (user_num, start_time, end_time, pricing_id)
);

COMMIT;