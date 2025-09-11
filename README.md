# Laboratorio 1 Programación de Aplicaciones

---
## Trabajando en el IDE

Antes de ejecutar el programa, entrar a mysql y pegar:

CREATE DATABASE culturarte CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'culturarte'@'localhost' IDENTIFIED BY 'culturarte123';
GRANT ALL PRIVILEGES ON culturarte.* TO 'culturarte'@'localhost';
FLUSH PRIVILEGES;
---
## Instalar en PC de la universidad

Entrar a la terminal, ir a la carpeta del proyecto (cd ruta/del/proyecto) y ejecutar:
* xhost +local:docker

En caso de ser necesario, instalarlo

Despues, ejecutar:
* sudo docker compose build
* sudo docker compose up
---



