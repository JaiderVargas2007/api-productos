package com.inventario.api_productos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// el test usa H2 para que no dependa de tener MySQL prendido
@SpringBootTest
@ActiveProfiles("h2")
class ApiProductosApplicationTests {

    @Test
    void contextLoads() {
    }

}
