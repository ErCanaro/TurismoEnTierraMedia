package tierraMedia;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsuarioTest {

	@Test
	public void notNull() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		assertNotNull(user);
	}

	@Test
	public void quePuedaAceptarUnaSugerenciaAtraccion() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		Atraccion at1 = new Atraccion("atraccion 1", TipoAtraccion.AVENTURA, 10, 1, 25);

		user.aceptarSugerencia(at1);

		assertTrue(user.getHistorialDeProductos().contains(at1));
		assertTrue(user.getItinerario().contains(at1));
	}

	@Test
	public void quePuedaAceptarUnaSugerenciaPromocionAbsoluta() {
		Usuario miUsuario = new Usuario("Elewen", TipoAtraccion.PAISAJES, 50, 8);
		Atraccion at1 = new Atraccion("Atraccion 1", TipoAtraccion.PAISAJES, 10, 1, 25);
		Atraccion at2 = new Atraccion("Atraccion 2", TipoAtraccion.PAISAJES, 5, 1.5, 25);
		PromocionAbsoluta promoPaisajes = new PromocionAbsoluta("Promo paisajes", TipoAtraccion.PAISAJES, at1, at2, 10);

		miUsuario.aceptarSugerencia(promoPaisajes);

		assertTrue(miUsuario.getHistorialDeProductos().contains(at1));
		assertTrue(miUsuario.getHistorialDeProductos().contains(at2));
		assertTrue(miUsuario.getItinerario().contains(promoPaisajes));
	}

	@Test
	public void queAlAceptarUnaPromocionAxBLasAtraccionesIncluidasSeGuardenEnElHistorial() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		Atraccion at1 = new Atraccion("Atraccion 1", TipoAtraccion.AVENTURA, 10, 1, 25);
		Atraccion at2 = new Atraccion("Atraccion 2", TipoAtraccion.AVENTURA, 10, 2, 15);
		Atraccion at3 = new Atraccion("Atraccion 3", TipoAtraccion.AVENTURA, 10, 3, 35);

		PromocionAxB promoAxB = new PromocionAxB("Hora de Aventura", TipoAtraccion.AVENTURA, at1, at2, at3);

		user.aceptarSugerencia(promoAxB);
		assertTrue(user.getHistorialDeProductos().contains(at1));
		assertTrue(user.getHistorialDeProductos().contains(at2));
		assertTrue(user.getHistorialDeProductos().contains(at3));
		assertTrue(user.getItinerario().contains(promoAxB));
	}

	@Test
	public void queAlAceptarUnaSugerenciaQueCuesta22QuedenEnElPresupuesto28() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		Atraccion at1 = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 1, 25);
		Atraccion at2 = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 12, 2, 15);
		Atraccion at3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 15, 3, 35);

		PromocionAxB promoAxB = new PromocionAxB("Hora de Aventura", TipoAtraccion.AVENTURA, at1, at2, at3);

		assertEquals(50, user.getPresupuesto(), 0);
		user.aceptarSugerencia(promoAxB);
		assertEquals(28, user.getPresupuesto(), 0);
	}

	@Test
	public void queAlAceptarUnaSugerenciaQueDura3HorasQueden5Disponibles() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);

		Atraccion at1 = new Atraccion("Moria", TipoAtraccion.AVENTURA, 10, 1, 25);
		Atraccion at2 = new Atraccion("Aventura atraccion", TipoAtraccion.AVENTURA, 12, 2, 15);

		PromoPorcentual promo1 = new PromoPorcentual("Promo-20% Aventura", TipoAtraccion.AVENTURA, at1, at2, 20);

		assertEquals(8, user.getTiempoDisponible(), 0);
		user.aceptarSugerencia(promo1);
		assertEquals(5, user.getTiempoDisponible(), 0);
	}

	@Test
	public void queSiTieneTiempoYNoTieneDineroNoPuedeComprarProductos() {
		// Dinero Disponible = 50 -- Dinero Necesario = 55
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 12);
		Atraccion at1 = new Atraccion("Tested 1", TipoAtraccion.AVENTURA, 10, 1, 25);
		Atraccion at2 = new Atraccion("No Compila 1", TipoAtraccion.AVENTURA, 12, 2, 15);
		Atraccion at3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 10, 3, 35);
		Atraccion at4 = new Atraccion("No se que poner", TipoAtraccion.DEGUSTACION, 33, 3, 35);

		// Si puede comprar
		PromocionAxB promoAxB = new PromocionAxB("Hora de Aventura", TipoAtraccion.AVENTURA, at1, at2, at3);
		user.aceptarSugerencia(promoAxB);
		assertEquals(28, user.getPresupuesto(), 0);

		// Ya no puede comprar
		user.aceptarSugerencia(at4);
		assertEquals(28, user.getPresupuesto(), 0);
		assertFalse(user.getHistorialDeAtracciones().contains(at4));
	}

	@Test
	public void queSiTieneDineroYNoTieneTiempoNoPuedeComprarProductos() {
		// Tiempo Disponible = 8 -- Tiempo Necesario = 9
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		Atraccion at1 = new Atraccion("Tested 1", TipoAtraccion.AVENTURA, 10, 1, 25);
		Atraccion at2 = new Atraccion("No Compila 1", TipoAtraccion.AVENTURA, 12, 2, 15);
		Atraccion at3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 10, 3, 35);
		Atraccion at4 = new Atraccion("No se que poner", TipoAtraccion.DEGUSTACION, 13, 3, 35);

		PromocionAxB promoAxB = new PromocionAxB("Hora de Aventura", TipoAtraccion.AVENTURA, at1, at2, at3);

		// Si puede comprar
		user.aceptarSugerencia(promoAxB);
		assertEquals(28, user.getPresupuesto(), 0);

		// ya no puede comprar
		user.aceptarSugerencia(at4);
		assertEquals(28, user.getPresupuesto(), 0);
		assertFalse(user.getHistorialDeAtracciones().contains(at4));
	}

	@Test
	public void queAlAceptarUnaPromocionSeResteCupoDeLaMismaYDeSusAtraccionesIncluidas() {

		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		Atraccion at1 = new Atraccion("Tested 1", TipoAtraccion.AVENTURA, 10, 1, 5);
		Atraccion at2 = new Atraccion("No Compila 1", TipoAtraccion.AVENTURA, 12, 2, 7);
		Atraccion at3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 10, 3, 4);

		PromocionAxB promoAxB = new PromocionAxB("Hora de Aventura", TipoAtraccion.AVENTURA, at1, at2, at3);

		user.aceptarSugerencia(promoAxB);
		assertEquals(28, user.getPresupuesto(), 0);
		assertEquals(3, promoAxB.getAtraccionesIncluidas().get(2).getCupo());
		assertEquals(at1.getCupo(), 4);
		assertEquals(at2.getCupo(), 6);
		assertEquals(at3.getCupo(), 3);
	}

	@Test
	public void queNoPuedaAceptarNingunProductoQueContengaUnaAtraccionAntesAdquirida() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 12);
		Atraccion at1 = new Atraccion("Tested 1", TipoAtraccion.AVENTURA, 10, 1, 25);
		Atraccion at2 = new Atraccion("No Compila 1", TipoAtraccion.AVENTURA, 12, 2, 15);
		Atraccion at3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 10, 3, 35);

		PromocionAxB promoAxB = new PromocionAxB("Hora de Aventura", TipoAtraccion.AVENTURA, at1, at2, at3);

		// Puede comprar porque es la primer compra.
		user.aceptarSugerencia(promoAxB);
		assertEquals(28, user.getPresupuesto(), 0);
		assertTrue(user.getHistorialDeAtracciones().contains(at3));
		assertTrue(user.getItinerario().contains(promoAxB));

		// No puede comprar porque la atraccion fue adquirida dentro de promoAxB.
		user.aceptarSugerencia(at3);
		assertEquals(28, user.getPresupuesto(), 0);
		assertTrue(user.getHistorialDeAtracciones().contains(at3));
		assertFalse(user.getItinerario().contains(at3));
	}

}
