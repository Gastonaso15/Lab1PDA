# Laboratorio 1 Programación de Aplicaciones


Antes de ejecutar el programa, entrar a mysql y pegar:

CREATE DATABASE culturarte CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'culturarte'@'localhost' IDENTIFIED BY 'culturarte123';
GRANT ALL PRIVILEGES ON culturarte.* TO 'culturarte'@'localhost';
FLUSH PRIVILEGES;
