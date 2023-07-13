CREATE DATABASE IF NOT EXISTS libraryDB;

USE libraryDB;

CREATE TABLE IF NOT EXISTS user (
  user_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  gender VARCHAR(255),
  phone_number VARCHAR(255),
  address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS books (
  book_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  author VARCHAR(255),
  genre VARCHAR(255),
  year VARCHAR(255),
  publisher VARCHAR(255),
  isbn VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_book(
    user_book_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    isbn VARCHAR(255),
    lentDate VARCHAR(255) DEFAULT NULL ,
    returnDate VARCHAR(255) DEFAULT NULL
);