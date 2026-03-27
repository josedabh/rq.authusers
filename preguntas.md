# Preguntas de refactorización (para alinear implementación)

## 1) Entidades JPA (prioridad alta)
1. **IDs de entidades:** ¿quieres estandarizar todos los IDs a `UUID` (incluyendo `Reward` y `UserChallenge`) o mantener algunos `Long` autoincrementales?
2. **Generación de UUID:** ¿te parece bien usar solo `@GeneratedValue(strategy = GenerationType.UUID)` en todas las entidades que usen UUID?
3. **Nombres de tablas/columnas:** ¿definimos una convención única (por ejemplo `UPPER_SNAKE_CASE`) para todas las tablas y columnas?
4. **Fechas:** ¿migramos todo a `java.time` (`LocalDateTime`) y eliminamos `java.util.Date` en entidades como `UserChallenge`?
5. **Relaciones y carga:** ¿quieres que todas las relaciones `@ManyToOne` sean explícitamente `fetch = FetchType.LAZY` por defecto?
6. **Estados y tipos:** en `Challenge`, ¿prefieres reemplazar campos `String` (como `verificationType`) por `enum` tipado?
7. **Campos transitorios:** `questionsCount` en `Challenge` está en `@Transient`; ¿quieres mantenerlo calculado en servicio o persistirlo en BD?
8. **Restricciones de BD:** ¿agregamos más `nullable = false`, `unique` e índices en entidades para reforzar integridad?

## 2) DTOs, mappers y contratos API
9. **Password en respuestas:** actualmente se mapea password en `UserResponse`; ¿confirmas que lo eliminamos completamente de cualquier respuesta API?
10. **Validaciones Bean Validation:** ¿aplicamos validaciones estrictas en DTOs (`@Email`, `@NotBlank`, `@Size`, `@Pattern`, `@Min`) y `@Valid` en todos los `@RequestBody`?
11. **Estandarización de mappers:** ¿quieres mantener mappers manuales actuales o migrar a un enfoque más consistente (manual con utilidades comunes / MapStruct)?
12. **Tipos en DTOs:** ¿dejamos IDs como `String` en requests/responses o los tipamos como `UUID` donde aplique?

## 3) Lógica de negocio y consistencia de dominio
13. **Comparaciones por estado:** ¿refactorizamos comparaciones por texto (`String`) a comparaciones directas con `enum` en servicios?
14. **Reglas de puntos y stock:** ¿quieres centralizar reglas de negocio (compra, puntos, stock, intentos) en métodos de dominio/servicio dedicados?
15. **Modelo UserChallenge:** ¿el estado de avance debe tener más detalle (ej. `IN_PROGRESS`, `COMPLETED`, `FAILED`) en vez de solo `boolean completed`?

## 4) Excepciones, errores y seguridad
16. **Jerarquía de excepciones:** ¿te parece crear una jerarquía uniforme de excepciones de negocio y mapearlas a HTTP status correctos (400/404/409/500)?
17. **Formato de error API:** ¿definimos una estructura única de error (código, mensaje, timestamp, path, detalles) para todos los endpoints?
18. **Validación de UUID/fechas:** ¿quieres utilidades comunes para parseo seguro y mensajes de error funcionales?

## 5) Estrategia de refactor y rollout
19. **Alcance:** ¿prefieres un refactor grande en una sola entrega o por fases (Entidades → DTO/Mapper → Servicios → Excepciones)?
20. **Compatibilidad BD:** ¿podemos hacer cambios de esquema incompatibles (migraciones), o debemos mantener compatibilidad total con la BD actual?
21. **Cobertura de pruebas:** ¿quieres que aprovechemos el refactor para ampliar pruebas (unitarias/integración) en los flujos críticos?

---

Si te parece, cuando respondas estas preguntas te propongo un **plan de implementación por fases** con cambios mínimos por PR para reducir riesgo.
