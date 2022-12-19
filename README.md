


# KiloAPI
[		![](https://raw.githubusercontent.com/TrayZNix/KilosAPI/development/images/logo.png?token=GHSAT0AAAAAABZYGYZAGMVPXGRGW6H2G5HYY5AM5HA)
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
Gracias a Postman, puedes probar las peticiones de una manera fácil y directa. Para ello, dentro del programa, solo debes importar el archivo [TRIANAFY-REQUESTS-POSTMAN.json]() ubicado en la carpeta raiz de *KiloAPI*

### Documentación de la API
Tras ejecutar la app, podemos acceder a su documentación accediendo a la siguiente URL

    http://localhost:8080/swagger-ui/index.html

Por otro lado, también es posible acceder al JSON que contiene toda la documentación mediante la siguiente URL

    http://localhost:8080/v3/api-docs

## Restricciones

### Restricciones en cuerpos de peticiones *POST* 

> #### Crear un destinatario: 
> 	`http://localhost:8080/destinatario/{id}` 
> 
> Los siguientes parámetro son **obligatorio**:
> 
>     {
> 	    "nombre": "nombreDelDestinatario",
>       "direccion": "direccionDestinatario", 
> 	    "telefono": "telefonoDelDestinatario",
>       "personaContacto": "personaContacto", 
>     }
>   ⠀
 


### Restricciones en cuerpos de peticiones *PUT*

> #### Modificar artista: 
> 	`http://localhost:8080/destinatario/{id}` 
> 
> Los siguientes parámetro son **obligatorio**:
> 
>     {
> 	    "nombre": "nombreDelDestinatario",
>       "direccion": "direccionDestinatario", 
> 	    "telefono": "telefonoDelDestinatario",
>       "personaContacto": "personaContacto", 
>     }
>   ⠀



### A tener en cuenta en las peticiones *DELETE*

>#### Borrado de un destinatario
>Al borrar un artista con la petición *DELETE* en la URL `localhost:8080/destinatario/{id}`, las cajas que se le asignaron previamente se conservarán, cambiando el atributo destinatario a null.
>⠀

>#### Borrado de una caja
>Al borrar una caja con la petición *DELETE* en la URL `localhost:8080/caja/{id}`, se borrarán todas las entradas en la relacion *Tiene*, y las cantidades de alimento que se habian asignado a las cajas se suman a *KilosDisponibles*.
>⠀
