CREATE DATABASE IF NOT EXISTS animal_shelter_managementsystem;

USE animal_shelter_managementsystem;

-- Table for adopter information
CREATE TABLE adopter_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    age INT,
    occupation VARCHAR(100),
    contact_number VARCHAR(20)
);

-- Table for animal information
CREATE TABLE animal_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    animal_name VARCHAR(100) NOT NULL,
    species VARCHAR(50) NOT NULL,
    age INT,
    health_status VARCHAR(50) DEFAULT 'Not Specified',
    adoption_status VARCHAR(50) DEFAULT 'Available',
    breed VARCHAR(100) DEFAULT 'Unknown',
    weight_kg DOUBLE DEFAULT 0.0,
    height_cm DOUBLE DEFAULT 0.0,
    eye_color VARCHAR(50) DEFAULT 'Not Specified',
    color_pattern VARCHAR(100) DEFAULT 'Not Specified',
    accepted_date DATE NOT NULL,
    adopted_date DATE DEFAULT NULL,
    adopter_id INT DEFAULT NULL,

    CONSTRAINT fk_animal_adopter
        FOREIGN KEY (adopter_id)
        REFERENCES adopter_info(id)
        ON DELETE SET NULL
);
