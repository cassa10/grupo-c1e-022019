# grupo-c1e-022019 [![Build Status](https://travis-ci.org/cassa10/grupo-c1e-022019.svg?branch=master)](https://travis-ci.org/cassa10/grupo-c1e-022019) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/f4981a75026a47caafc74853766908f9)](https://www.codacy.com/manual/cassa10/grupo-c1e-022019?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cassa10/grupo-c1e-022019&amp;utm_campaign=Badge_Grade)

# Proyecto para Desarrollo de Aplicaciones UNQ.

----

## Heroku:
 - Frontend: [![Deploy](https://www.herokucdn.com/deploy/button.png)](https://grupo-c1e-022019-frontend.herokuapp.com/)

 - Backend: [![Deploy](https://www.herokucdn.com/deploy/button.png)](https://grupo-c1e-022019.herokuapp.com/)

### [Enunciado del proyecto](2019.02.Enunciado_DocumentoDeVision.pdf)

## Para correr el proyecto localmente

### Prerequisitos
    - Tener instalado: 
        - nodejs
        - npm
        - maven 3.2 o mas reciente
        - jdk 8 o mas reciente
        - postgreSQL (corriendo)

### Backend
    - Abrir un bash-shell
    - Ir al directorio backend-grupo-c1e-022019
    - Usar el comando 'mvn install' para instalar las dependecias.
    - Configurar application.properties, es decir, configurar las variables de entorno a necesidad y preferencia. Sino poner por default las del application-local.properties tal cual.
    - Si se eligio configurar con la configuracion de application-local.properties, es necesario crear una base de datos con el nombre 'ViendasApp' (sin ''). 
    - Usar el comando 'mvn spring-boot:run' para correr la Aplicacion.
    - Si no funciona el comando anterior, utilizar el siguiente: 'java -jar target/mywebserviceapp-0.0.1-SNAPSHOT.jar' 

### Frontend
    - Abrir un bash-shell
    - Ir al directorio frontend-grupo-c1e-022019
    - Usar el comando 'npm install' (Si es la primera vez que lo corres)
    - Usar el comando 'npm start'
    - Abrir un browser con el address localhost:3000
