CREATE DATABASE IF NOT EXISTS airline_system;
USE airline_system;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Flights table
CREATE TABLE IF NOT EXISTS flights (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    airline VARCHAR(100),
    source VARCHAR(100),
    destination VARCHAR(100),
    departure_time TIME,
    arrival_time TIME,
    price DOUBLE,
    seats_available INT
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    flight_id INT,
    seat_count INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (flight_id) 
    REFERENCES flights(flight_id)
);


-- Insert sample users
INSERT INTO users (username, password) VALUES
('admin', 'admin123'),
('john_doe', 'pass123');

CREATE TABLE face_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    face_id INT UNIQUE
);



-- Insert sample flights
INSERT INTO flights (airline, source, destination, departure_time,arrival_time, price, seats_available) VALUES
('Air India', 'Delhi', 'Mumbai', '10:00:00','01:00:00', 4500, 50),
('IndiGo', 'Chennai', 'Kolkata', '15:30:00','18:00:00', 4000, 60),
('Indigo', 'Delhi', 'Bangalore', '18:00:00','20:30:00', 6000, 40),
('Vistara', 'Bangalore', 'Delhi', '09:00:00','11:30:00', 5200, 45),
('GoAir', 'Mumbai', 'Hyderabad', '12:20:00','01:50:00', 4100, 30),
('AirAsia', 'Hyderabad', 'Chennai', '07:50:00','09:00:00', 3800, 25),
('Akasa Air', 'Kolkata', 'Chennai', '20:00:00','04:30:00', 5600, 35),
('Alliance Air', 'Delhi', 'Chennai', '11:10:00','01:40:00', 4700, 40),
('Star Air', 'Mumbai', 'Bangalore', '16:30:00','18:00:00', 4950, 28),
('TruJet', 'Chennai', 'Hyderabad', '13:45:00','14:55:00', 4350, 33),
('FlyBig', 'Delhi', 'Hyderabad', '17:15:00','19:25:00', 4600, 38),
('IndiGo', 'Bangalore', 'Mumbai', '14:10:00','16:00:00', 4400, 50),
('Air India Express', 'Kolkata', 'Delhi', '06:30:00','08:45:00', 4800, 42),
('SpiceJet', 'Chennai', 'Delhi', '08:00:00','10:30:00', 5000, 30),
('GoAir', 'Hyderabad', 'Bangalore', '19:20:00','20:30:00', 4250, 36),
('GoAir', 'Chennai', 'Mumbai', '12:20:00','02:35:00', 4200, 36),
('GoAir', 'Delhi', 'Mumbai', '12:20:00','02:4:00', 4200, 36);

-- Insert sample bookings
INSERT INTO bookings (user_id, flight_id, seat_count) VALUES
(2, 1, 2),
(2, 3, 1),
(3,2,1),
(4,5,3);
INSERT INTO face_users(username,face_id)VALUES("silus",0),("dhanush",1);
