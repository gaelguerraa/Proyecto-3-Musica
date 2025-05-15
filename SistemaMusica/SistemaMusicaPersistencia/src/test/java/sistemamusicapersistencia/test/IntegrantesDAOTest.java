/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    private Integrante integranteGuardado2;
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

            if (integranteGuardado2 != null) {
                coleccion.deleteOne(Filters.eq(CAMPO_ID, integranteGuardado2.getId()));
                integranteGuardado2 = null;
            }
        }
    }

    @Test
    public void testAgregarIntegranteSinFechaSalida() {
        LocalDate fechaIngreso = LocalDate.of(1999, Month.MARCH, 15);
        Date fechaIngresoDate = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());

        IntegranteDTO nuevoIntegrante = new IntegranteDTO(
                "Billie Joe Armstrong",
                RolIntegrante.VOCALISTA,
                fechaIngresoDate,
                null,
                true
        );

        Integrante integranteAgregado = integrantesDAO.agregarIntegrante(nuevoIntegrante);
        integranteGuardado = integranteAgregado; // Comentar esta linea para ver en MongoDB

        assertEquals("Billie Joe Armstrong", integranteAgregado.getNombre());
        assertEquals(RolIntegrante.VOCALISTA, integranteAgregado.getRol());
        assertEquals(fechaIngresoDate, integranteAgregado.getFechaIngreso());
        assertNull(integranteAgregado.getFechaSalida());
        assertTrue(integranteAgregado.isActivo());

    }

    @Test
    public void testAgregarIntegranteConFechaSalida() {
        LocalDate fechaIngreso = LocalDate.of(1999, Month.MARCH, 15);
        LocalDate fechaSalida = LocalDate.of(2017, Month.JULY, 20);

        Date fechaIngresoDate = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaSalidaDate = Date.from(fechaSalida.atStartOfDay(ZoneId.systemDefault()).toInstant());

        IntegranteDTO nuevoIntegrante = new IntegranteDTO(
                "Chester Bennington",
                RolIntegrante.VOCALISTA,
                fechaIngresoDate,
                fechaSalidaDate,
                false
        );

        Integrante integranteAgregado = integrantesDAO.agregarIntegrante(nuevoIntegrante);
        integranteGuardado = integranteAgregado; // Comentar esta linea para ver en MongoDB

        assertEquals("Chester Bennington", integranteAgregado.getNombre());
        assertEquals(RolIntegrante.VOCALISTA, integranteAgregado.getRol());
        assertEquals(fechaIngresoDate, integranteAgregado.getFechaIngreso());
        assertEquals(fechaSalidaDate, integranteAgregado.getFechaSalida());
        assertFalse(integranteAgregado.isActivo());

    }

    @Test
    public void testConsultarTodosIntegrantesOk() {
        final int CANTIDAD_LISTA = 2;

        LocalDate fechaIngreso = LocalDate.of(1999, Month.MARCH, 15);
        LocalDate fechaSalida = LocalDate.of(2017, Month.JULY, 20);
        
        Date fechaIngresoDate = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaSalidaDate = Date.from(fechaSalida.atStartOfDay(ZoneId.systemDefault()).toInstant());

        IntegranteDTO nuevoIntegrante = new IntegranteDTO(
                "Chester Bennington",
                RolIntegrante.VOCALISTA,
                fechaIngresoDate,
                fechaSalidaDate,
                false
        );

        Integrante integranteAgregado = integrantesDAO.agregarIntegrante(nuevoIntegrante);
        integranteGuardado = integranteAgregado; // Comentar esta linea para ver en MongoDB

        IntegranteDTO nuevoIntegrante2 = new IntegranteDTO(
                "Billie Joe Armstrong",
                RolIntegrante.VOCALISTA,
                fechaIngresoDate,
                null,
                true
        );

        Integrante integranteAgregado2 = integrantesDAO.agregarIntegrante(nuevoIntegrante2);
        integranteGuardado2 = integranteAgregado2; // Comentar esta linea para ver en MongoDB

        List<Integrante> listaIntegrantesObtenida = integrantesDAO.consultarTodos();

        assertEquals(CANTIDAD_LISTA, listaIntegrantesObtenida.size());

    }

    @Test
    public void testConsultarIntegrantePorId() {
        LocalDate fechaIngreso = LocalDate.of(1999, Month.MARCH, 15);
        LocalDate fechaSalida = LocalDate.of(2017, Month.JULY, 20);
        
        Date fechaIngresoDate = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaSalidaDate = Date.from(fechaSalida.atStartOfDay(ZoneId.systemDefault()).toInstant());

        IntegranteDTO nuevoIntegrante = new IntegranteDTO(
                "Chester Bennington",
                RolIntegrante.VOCALISTA,
                fechaIngresoDate,
                fechaSalidaDate,
                false
        );

        Integrante integranteAgregado = integrantesDAO.agregarIntegrante(nuevoIntegrante);
        integranteGuardado = integranteAgregado; // Comentar esta linea para ver en MongoDB

        System.out.println(integranteAgregado.getId().toString());

        String ID_OBTENIDA = integranteAgregado.getId().toString();

        Integrante integranteObtenido = integrantesDAO.consultarPorId(ID_OBTENIDA);

        assertNotNull(integranteObtenido);
        assertEquals(ID_OBTENIDA, integranteObtenido.getId().toString());
    }

}
