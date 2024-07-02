BEGIN;

DROP TABLE IF EXISTS line_detail CASCADE;
DROP TABLE IF EXISTS landmark_exit_info CASCADE;
DROP TABLE IF EXISTS bus_exit_info CASCADE;
DROP TABLE IF EXISTS ongoing_ride CASCADE;
DROP TABLE IF EXISTS ride CASCADE;
DROP TABLE IF EXISTS route_pricing CASCADE;
DROP TABLE IF EXISTS station CASCADE;
DROP TABLE IF EXISTS passenger CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS line CASCADE;

DROP PROCEDURE IF EXISTS handle_after_insert_ride;
DROP PROCEDURE IF EXISTS handle_after_update_ride;
DROP PROCEDURE IF EXISTS handle_before_delete_ride;

DROP TRIGGER IF EXISTS after_insert_ride;
DROP TRIGGER IF EXISTS after_update_ride;
DROP TRIGGER IF EXISTS before_delete_ride;

COMMIT;

BEGIN;

CREATE TABLE IF NOT EXISTS station
(
    english_name VARCHAR(50) PRIMARY KEY,
    chinese_name VARCHAR(50) NOT NULL,
    district     VARCHAR(50),
    intro        TEXT,
    status       VARCHAR(20) NOT NULL,
    UNIQUE (chinese_name)
);

CREATE TABLE IF NOT EXISTS bus_exit_info
(
    bus_exit_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    station_name     VARCHAR(50)  NOT NULL,
    exit_gate        VARCHAR(100) NOT NULL,
    bus_name         VARCHAR(50)  NOT NULL,
    bus_line         VARCHAR(50)  NOT NULL,

    FOREIGN KEY (station_name) REFERENCES station (english_name),
    UNIQUE (station_name, exit_gate, bus_line, bus_name)
);

CREATE TABLE IF NOT EXISTS landmark_exit_info
(
    landmark_exit_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    station_name          VARCHAR(50)  NOT NULL,
    exit_gate             VARCHAR(100) NOT NULL,
    landmark              VARCHAR(50)  NOT NULL,
    FOREIGN KEY (station_name) REFERENCES station (english_name),
    UNIQUE (station_name, exit_gate, landmark)
);

CREATE TABLE IF NOT EXISTS line
(
    line_id       INT AUTO_INCREMENT PRIMARY KEY,
    line_name     VARCHAR(50) UNIQUE NOT NULL,
    start_time    TIME              NOT NULL,
    end_time      TIME              NOT NULL,
    intro         TEXT,
    mileage       REAL,
    color         VARCHAR(20),
    first_opening DATE              NOT NULL,
    url           VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS line_detail
(
    line_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    line_name      VARCHAR(50)  NOT NULL,
    station_name   VARCHAR(50) NOT NULL,
    station_order  INTEGER     NOT NULL,
    UNIQUE (line_name, station_name),
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
    price         FLOAT       NOT NULL,
    FOREIGN KEY (start_station) REFERENCES station (english_name),
    FOREIGN KEY (end_station) REFERENCES station (english_name),
    UNIQUE (start_station, end_station)
);

CREATE TABLE IF NOT EXISTS ride
(
    ride_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_num      VARCHAR(20)  NOT NULL,
    auth_type     VARCHAR(20)  NOT NULL,
    start_time    TIMESTAMP(0) NOT NULL,
    end_time      TIMESTAMP(0),
    duration      BIGINT,
    start_station VARCHAR(50)  NOT NULL,
    end_station   VARCHAR(50),
    class         VARCHAR(20)  NOT NULL,
    price         FLOAT,
    FOREIGN KEY (start_station) REFERENCES station (english_name),
    FOREIGN KEY (end_station) REFERENCES station (english_name)
);

CREATE TABLE IF NOT EXISTS ongoing_ride
(
    ride_id       BIGINT PRIMARY KEY,
    user_num      VARCHAR(20)  NOT NULL,
    auth_type     VARCHAR(20)  NOT NULL,
    start_time    TIMESTAMP(0) NOT NULL,
    start_station VARCHAR(50)  NOT NULL,
    class         VARCHAR(20)  NOT NULL,
    FOREIGN KEY (ride_id) REFERENCES ride (ride_id),
    FOREIGN KEY (start_station) REFERENCES station (english_name)
);

COMMIT;

BEGIN;

DELIMITER //

CREATE PROCEDURE handle_after_insert_ride(
    IN ride_id BIGINT,
    IN user_num VARCHAR(20),
    IN auth_type VARCHAR(20),
    IN start_time TIMESTAMP,
    IN end_time TIMESTAMP,
    IN duration INT,
    IN start_station VARCHAR(50),
    IN end_station VARCHAR(50),
    IN class VARCHAR(20)
)
BEGIN
    IF end_time IS NULL OR end_station IS NULL OR end_station = '' THEN
        INSERT INTO ongoing_ride (ride_id, user_num, auth_type, start_time, start_station, class)
        VALUES (ride_id, user_num, auth_type, start_time, start_station, class);
    END IF;
END //

CREATE PROCEDURE handle_after_update_ride(
    IN old_ride_id BIGINT,
    IN old_end_time TIMESTAMP,
    IN old_end_station VARCHAR(50),
    IN new_end_time TIMESTAMP,
    IN new_end_station VARCHAR(50)
)
BEGIN
    IF old_end_time IS NULL OR old_end_station IS NULL OR old_end_station = '' THEN
        IF new_end_time IS NOT NULL AND new_end_station IS NOT NULL AND new_end_station != '' THEN
            DELETE FROM ongoing_ride WHERE ride_id = old_ride_id;
        END IF;
    END IF;
END //

CREATE PROCEDURE handle_before_delete_ride(
    IN old_ride_id BIGINT
)
BEGIN
    DELETE FROM ongoing_ride WHERE ride_id = old_ride_id;
END //

CREATE TRIGGER after_insert_ride
    AFTER INSERT
    ON ride
    FOR EACH ROW
BEGIN
    CALL handle_after_insert_ride(
            NEW.ride_id,
            NEW.user_num,
            NEW.auth_type,
            NEW.start_time,
            NEW.end_time,
            NEW.duration,
            NEW.start_station,
            NEW.end_station,
            NEW.class
         );
END //

CREATE TRIGGER after_update_ride
    AFTER UPDATE
    ON ride
    FOR EACH ROW
BEGIN
    CALL handle_after_update_ride(
            OLD.ride_id,
            OLD.end_time,
            OLD.end_station,
            NEW.end_time,
            NEW.end_station
         );
END //

CREATE TRIGGER before_delete_ride
    BEFORE DELETE
    ON ride
    FOR EACH ROW
BEGIN
    CALL handle_before_delete_ride(OLD.ride_id);
END //

DELIMITER ;

COMMIT;