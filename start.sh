#!/bin/bash
# Permitir que el contenedor acceda al X server del host
export DISPLAY=${DISPLAY:-:0}
xhost +local:docker

# Ejecutar la app Java
java --enable-preview -jar app.jar

