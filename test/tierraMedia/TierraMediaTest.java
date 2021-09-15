package tierraMedia;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TierraMediaTest {
	TierraMedia tm = new TierraMedia();

	@Test
	public void queAlCargarDatosDesdeArchivoSeGuardenEnLosAtributosCorrespondientes() {
		tm.cargarDatosDesdeArchivo();
		assertEquals(11, tm.getAtraccionesDB().size());
		assertEquals(9, tm.getPromocionesDB().size());
		assertEquals(20, tm.getProductosDB().size());
		assertEquals(5, tm.getUsuariosDB().size());
	}

	@Test
	public void quePuedaOrdenarUnaSugerencia() {
		tm.cargarDatosDesdeArchivo();

		Usuario u1 = tm.getUsuariosDB().get(4);

		// Primer y último producto en productos disponibles, antes de ordenarlos,
		// en el orden de carga.
		assertEquals("Moria", tm.getProductosDB().get(0).getNombre());
		assertEquals("Promo Degustacion", tm.getProductosDB().get(tm.getProductosDB().size() - 1).getNombre());

		// Primer y último producto en la sugerencia, ya ordenados para el usuario.
		assertEquals("Promo Aventura", tm.ordenarSugerencia(u1).get(0).getNombre());
		assertEquals("La Comarca", tm.ordenarSugerencia(u1).get(tm.ordenarSugerencia(u1).size() - 1).getNombre());

	}
}
