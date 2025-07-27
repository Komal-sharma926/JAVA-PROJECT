CREATE DATABASE CollegeMini;

USE CollegeMini;

CREATE TABLE Students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    marks INT,
    course VARCHAR(100),
    status VARCHAR(20)
);
