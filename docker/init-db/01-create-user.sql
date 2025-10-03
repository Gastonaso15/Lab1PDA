-- Crear usuario culturarte y otorgar permisos
CREATE USER IF NOT EXISTS 'culturarte'@'%' IDENTIFIED BY 'culturarte123';
GRANT ALL PRIVILEGES ON culturarte.* TO 'culturarte'@'%';
FLUSH PRIVILEGES;

-- Verificar que el usuario fue creado
SELECT User, Host FROM mysql.user WHERE User = 'culturarte';
