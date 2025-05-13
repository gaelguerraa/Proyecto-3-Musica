/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.integrantesTest;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicapersistencia.implementaciones.IntegrantesDAO;
import sistemamusicapersistencia.implementaciones.ManejadorConexiones;

/**
 *
 * @author PC
 */
public class IntegrantesDAOTest {

    public IntegrantesDAOTest() {
    }

    private Integrante integranteGuardado;
    private final IntegrantesDAO integrantesDAO = new IntegrantesDAO();
    private final String COLECCION = "integrantes";
    private final String CAMPO_ID = "_id";

    @BeforeAll
    public static void activarModoPruebas() {
        ManejadorConexiones.activateTestMode();
    }

    @AfterEach
    public void limpiarBD() {
        if (integranteGuardado != null) {
            MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Integrante> coleccion = baseDatos.getCollection(
                    COLECCION, Integrante.class);

            coleccion.deleteOne(Filters.eq(CAMPO_ID, integranteGuardado.getId()));
            integranteGuardado = null;
        }
    }

    @Test
    public void testAgregarIntegranteSinFechaSalida() {
        LocalDate fechaIngreso = LocalDate.of(1987, Month.MARCH, 15);

        IntegranteDTO nuevoIntegrante = new IntegranteDTO(
                "Billie Joe Armstrong",
                RolIntegrante.VOCALISTA,
                fechaIngreso,
                null,
                true
        );

        Integrante integranteAgregado = integrantesDAO.agregarIntegrante(nuevoIntegrante);
        integranteGuardado = integranteAgregado;

        assertEquals("Billie Joe Armstrong", integranteAgregado.getNombre());
        assertEquals(RolIntegrante.VOCALISTA, integranteAgregado.getRol());
        assertEquals(fechaIngreso, integranteAgregado.getFechaIngreso());
        assertNull(integranteAgregado.getFechaSalida());
        assertTrue(integranteAgregado.isActivo());

    }

    @Test
    public void testAgregarIntegranteConFechaSalida() {
        LocalDate fechaIngreso = LocalDate.of(1999, Month.MARCH, 15);
        LocalDate fechaSalida = LocalDate.of(2017, Month.JULY, 20);

        IntegranteDTO nuevoIntegrante = new IntegranteDTO(
                "Chester Bennington",
                RolIntegrante.VOCALISTA,
                fechaIngreso,
                fechaSalida,
                true
        );

        Integrante integranteAgregado = integrantesDAO.agregarIntegrante(nuevoIntegrante);
        integranteGuardado = integranteAgregado;

        assertEquals("Chester Bennington", integranteAgregado.getNombre());
        assertEquals(RolIntegrante.VOCALISTA, integranteAgregado.getRol());
        assertEquals(fechaIngreso, integranteAgregado.getFechaIngreso());
        assertEquals(fechaSalida, integranteAgregado.getFechaSalida());
        assertTrue(integranteAgregado.isActivo());

    }

}
