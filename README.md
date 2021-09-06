##Especificaciones
1. Hace uso de la API de Marvel (https://developer.marvel.com/docs)
2. Obtener el listado de personajes

##Implementación
##### Arquitectura y Patrones
- Se ha diseñado el proyecto para aplicar el patrón de Clean Architecture MVVM de tal manera que desde la vista (Fragment) se observa una variable de estado que se encuentra en el ViewModel el cual comunica el resultado de la petición mediante los disntitos estados y actualiza la UI

- Para mantener lo máximo posible el patro Clean Architectuer, se han implemetando también los patrones de diseño  Repository y UseCase para una mayor claridad y limpieza

##### Librerias e Implementaciones
- De cara a tener una navegación por la app más clara y fácil de desarrollar si la applicación fuese creciendo, he implementado el componente `Navigation` de JetPack

- Dado que se ha dejado obsoleto el synthetic de kotlin, he usado ViewBinding para enlazar de manera más limpia el XML y la clase de cada vista evitando librerías de terceros (como `ButterKnife`) y métodos antiguos (como `findViewById<>()`)

- En lo que a librerías de terceros respecta: se ha usado `Hilt-Dagger` para la injección de dependencias teniendo en cuenta las recomendaciones de Android Developers (https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419) se han usado `okHttp3` y `Retrofit2` para las conezciones y llamadas a internet; `Coroutines` para los procesos asíncronos de la app; `Glide` para la carga de imágenes provenientes de internet; por útlimo, se ha usado `Lottie` para la animación de la imagen de carga de datos


##### Comentarios sobre el ciclo de vida del proyecto
- Durante la evolución del proyecto, fui implementado los distintos requerimientos pedidos y encontrando maneras de optimizarlos, por eso se verá en el repositorio dos ramas de la feature hero_detail. Una lo hace mediante una consulta al servicio específico y otr lo hace trayéndo los datos de la vista anterior.

- En el repositorio se ha implementado la metodología GitFlow, teniendo las 4 ramas de las implementaciones, una rama develop y una rama máster. La situacion de las ramas es la siguiente:
    * Develop: con las ramas mergeadas para tener el proyecto en el último estado óptimo posible
    * Master: con el estado del proyecto que se encuentra en la store (no se ha subido la app a ninguna store, es por darle algo de "realidad")
    * heroes_list || hero_detail_without_service || multiple_grid_layout_values: mergeadas a develop
    * hero_detail: está sin mergear dado que se encontró una implementación mejor (hero_detail_without_service) y ésta quedó sin formar parte del desarrollo
    
##Mejoras Futuras
1. Teniendo en cuenta el amplio listado de héroes que hay, sería hacer un buscador para facilitar el poder encontrar un héroe de manera ágil
2. Cachear la información del servicio la primera vez para mostrar los datos de una manera más rápida las siguientes veces. En caso de que el total de héroes sea distinto, realizar la carga de los datos de nuevo con un mensaje al usuario del tipo "Los datos que estas viendo son antiguos, recarga"
3. Mostrar más información de los héroes en el detalle (como las series, o cómics donde salen)
4. Catalogar el listado por héroes, series, cómics...
5. Mejorar la UX
6. Descargar el detalle del héroe 