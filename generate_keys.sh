#!/bin/bash
# generate_keys.sh
# Script para generar claves RSA para JWT

# Crear directorio si no existe
mkdir -p src/main/resources/

# Generar clave privada
openssl genrsa -out src/main/resources/privateKey.pem 2048

# Extraer clave pública de la clave privada
openssl rsa -in src/main/resources/privateKey.pem -pubout -out src/main/resources/publicKey.pem

echo "Claves RSA generadas correctamente en src/main/resources/"