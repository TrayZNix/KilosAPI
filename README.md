


# KiloAPI
![Logo de la operacion KILO](https://raw.githubusercontent.com/TrayZNix/KilosAPI/main/images/logo.png)

**Kiloapi** es una *API* que nos permite gestionar la campaña Operación Kilo que se realiza todos los años en el mes de diciembre en nuestro colegio.

## Requisitos
Para la ejecución de este proyecto, es necesario tener instalado [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), y [Apache Maven](https://maven.apache.org/download.cgi).
Además, para poder probar todas las peticiones más fácilmente, se recomienda el uso de [Postman](https://www.postman.com).

## Ejecución 
Para ejecutar el programa, debemos abrir CMD, ubicarnos en la carpeta del proyecto, y ejecutar el siguiente comando

    mvn spring-boot:run
**IMPORTANTE:**
Si el puerto 8080 se encuentra ocupado, la API no se iniciará. De ocurrir esto, debes introducir la siguiente linea en el archivo *application.properties* ubicado en *./src/main/resources*

	server.port=9000
*Si el puerto 9000 tampoco estuviese libre, puedes probar con otros*
## Peticiones

### Testing con Postman
Gracias a Postman, puedes probar las peticiones de una manera fácil y directa. Para ello, dentro del programa, solo debes importar el archivo [kilosAPI.postman_collection.json](https://github.com/TrayZNix/KilosAPI/blob/main/kilosAPI.postman_collection.json) ubicado en la carpeta raiz de *KiloAPI*

### Documentación de la API
Tras ejecutar la app, podemos acceder a su documentación accediendo a la siguiente URL

    http://localhost:8080/swagger-ui/index.html

Por otro lado, también es posible acceder al JSON que contiene toda la documentación mediante la siguiente URL

    http://localhost:8080/v3/api-docs
