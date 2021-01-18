## Cupon Meli Challenge

> Servicio que, a partir de un monto y una lista de items(con precio) determinada, maximiza la compra de items sin exeder el monto disponible.

### Tecnologias

* JAVA >= 8
* MAVEN
* SPRING BOOT
* JUnit

### Pre-requisitos
Para poder ejecutar el servicio, debes tener una versión de Java >= 8 y Maven para instalar las dependencias.

### Ejecutar servicio

Parados en el root del proyecto ejecutamos

```sh
1. mvn compile
2. mvn exec:java -Dexec.mainClass="com.meli.coupon.CouponApplication"
```

Esto nos pondra a correr el servicio en el puerto 8080

Para probar el servicio de forma online

https://challenge-coupon.herokuapp.com/
```curl
curl -H "Content-Type: application/json" -X POST https://challenge-coupon.herokuapp.com/coupon -d "{\"item_ids\":[\"MLA635957485\",\"MLA821631446\", \"MLA898568819\"],\"amount\":6000}"
```
### Tests

```sh
mvn test
```
