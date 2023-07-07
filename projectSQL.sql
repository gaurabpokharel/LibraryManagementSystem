CREATE DATABASE IF NOT EXISTS libraryDB;

USE libraryDB;

CREATE TABLE IF NOT EXISTS user (
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  gender VARCHAR(255),
  phone_number VARCHAR(255),
  address VARCHAR(255)
);



