# Preguntas de refactorización (para alinear implementación)

## 1) Entidades JPA (prioridad alta)
1. **IDs de entidades:** ¿quieres estandarizar todos los IDs a `UUID` (incluyendo `Reward` y `UserChallenge`) o mantener algunos `Long` autoincrementales? R: Quiero que sean ids que no sean pesados para que cuando lo llame no se trabe la base de datos. estaba bien antes pero es un poc ineficiente asi que piensa como podrias hacer los ids para que no haya problemas y dame las opcione soara que pueda eligir.
2. **Generación de UUID:** ¿te parece bien usar solo `@GeneratedValue(strategy = GenerationType.UUID)` en todas las entidades que usen UUID? R: Depende de como hagas la Pregunt 1 asi que esto se queda pendiente.
3. **Nombres de tablas/columnas:** ¿definimos una convención única (por ejemplo `UPPER_SNAKE_CASE`) para todas las tablas y columnas? R: No hace flata que toques lo nombre ta que etsan bien.
4. **Fechas:** ¿migramos todo a `java.time` (`LocalDateTime`) y eliminamos `java.util.Date` en entidades como `UserChallenge`? R: Si hazlo y crea un util oara cuando lo uses y no falle en front por conversion.
5. **Relaciones y carga:** ¿quieres que todas las relaciones `@ManyToOne` sean explícitamente `fetch = FetchType.LAZY` por defecto? R: Si quiero que sea persistence bag oara que el servidor no se sobrecargue.
6. **Estados y tipos:** en `Challenge`, ¿prefieres reemplazar campos `String` (como `verificationType`) por `enum` tipado? R: Si crea un enum en la carpeta enum o constants.
7. **Campos transitorios:** `questionsCount` en `Challenge` está en `@Transient`; ¿quieres mantenerlo calculado en servicio o persistirlo en BD? R: Si calculo en el servicio ya qeu la base de datos no le sirve.
8. **Restricciones de BD:** ¿agregamos más `nullable = false`, `unique` e índices en entidades para reforzar integridad? R: Eso depende del remodelado de esas tablas que me haras de la pregunta 1.

## 2) DTOs, mappers y contratos API
9. **Password en respuestas:** actualmente se mapea password en `UserResponse`; ¿confirmas que lo eliminamos completamente de cualquier respuesta API? R: Si hazlo y mira si no rompe del codigo. Intenta arreglarlo si no puedes dame el proceso que quieres realizar en un md aparte.
10. **Validaciones Bean Validation:** ¿aplicamos validaciones estrictas en DTOs (`@Email`, `@NotBlank`, `@Size`, `@Pattern`, `@Min`) y `@Valid` en todos los `@RequestBody`? R: Si Hazlo
11. **Estandarización de mappers:** ¿quieres mantener mappers manuales actuales o migrar a un enfoque más consistente (manual con utilidades comunes / MapStruct)? R: Quiero que sea de forma manual ya que puede fallarme el mapstruct.
12. **Tipos en DTOs:** ¿dejamos IDs como `String` en requests/responses o los tipamos como `UUID` donde aplique? R: Depende ya que quiero que el frontend tenga alguna forma de identificarlo dame en este respuesta md una explicación de si es esto importante y yo te respondo que vas a hcaer la final.

## 3) Lógica de negocio y consistencia de dominio
13. **Comparaciones por estado:** ¿refactorizamos comparaciones por texto (`String`) a comparaciones directas con `enum` en servicios? R: Si se puede si, sino hacer una compracion con equals para no tener que hacer logica innecsaria.
14. **Reglas de puntos y stock:** ¿quieres centralizar reglas de negocio (compra, puntos, stock, intentos) en métodos de dominio/servicio dedicados? R: Intenata darme algo visual para que lo apruebe ya que es confuso lo que quieres hacer y explica brevemente de porque lo harias asi.
15. **Modelo UserChallenge:** ¿el estado de avance debe tener más detalle (ej. `IN_PROGRESS`, `COMPLETED`, `FAILED`) en vez de solo `boolean completed`? R: Intenta crear un enum que contega mas pasos, pero antes restructura ese enun de como lo quiere hacer.

## 4) Excepciones, errores y seguridad
16. **Jerarquía de excepciones:** ¿te parece crear una jerarquía uniforme de excepciones de negocio y mapearlas a HTTP status correctos (400/404/409/500)? R: Si crealos y que se pueda usar en todo el proyecto.
17. **Formato de error API:** ¿definimos una estructura única de error (código, mensaje, timestamp, path, detalles) para todos los endpoints? R: Si definela y unelo en un errrors propertirss y que haya un header donde se oueda cambiar el idioma si tienes al lado de error un locale si no el erorrs que esta definido por defecto. 
18. **Validación de UUID/fechas:** ¿quieres utilidades comunes para parseo seguro y mensajes de error funcionales? R: Desarrola mejor esta pregunta ya que no esta claro loq qeu intentas decir.

## 5) Estrategia de refactor y rollout
19. **Alcance:** ¿prefieres un refactor grande en una sola entrega o por fases (Entidades → DTO/Mapper → Servicios → Excepciones)? R: Hazlo por fases para que pueda ver el seguimiento.
20. **Compatibilidad BD:** ¿podemos hacer cambios de esquema incompatibles (migraciones), o debemos mantener compatibilidad total con la BD actual? R: Esto creo que lo vas a tener que decirme en la siguiente cuando lo tenga claro.
21. **Cobertura de pruebas:** ¿quieres que aprovechemos el refactor para ampliar pruebas (unitarias/integración) en los flujos críticos? R: Si que supere el 70% del codigo entero del proyecto. Aplica lo Junits que creas necesarios.

---

Si te parece, cuando respondas estas preguntas te propongo un **plan de implementación por fases** con cambios mínimos por PR para reducir riesgo.
