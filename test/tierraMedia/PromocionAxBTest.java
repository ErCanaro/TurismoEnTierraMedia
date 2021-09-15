package tierraMedia;

import static org.junit.Assert.*;

import org.junit.Test;

public class PromocionAxBTest {

	@Test
	public void queSeCreeCorrectamente() {
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);
		Atraccion atracc2 = new Atraccion("Prueba 2", TipoAtraccion.AVENTURA, 12, 2, 15);
		Atraccion atracc3 = new Atraccion("Prueba 3", TipoAtraccion.AVENTURA, 10, 3, 35);

		PromocionAxB promoAxB = new PromocionAxB("Promo AxB", TipoAtraccion.AVENTURA, atracc, atracc2, atracc3);

		assertEquals("Promo AxB", promoAxB.getNombre());
		assertEquals(TipoAtraccion.AVENTURA, promoAxB.getTipo());
		assertEquals(3, promoAxB.getAtraccionesIncluidas().size());
		assertTrue(promoAxB.getAtraccionesIncluidas().contains(atracc));
		assertTrue(promoAxB.getAtraccionesIncluidas().contains(atracc2));
		assertTrue(promoAxB.getAtraccionesIncluidas().contains(atracc3));
		assertEquals(22, promoAxB.getCosto(), 0);
		assertEquals(10, promoAxB.getDuracion(), 0);
	}

	@Test
	public void queAlcomprarUnaPromocionAxBSeRestaElCupoDeCadaAtraccionIncluida() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 100);
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);
		Atraccion atracc2 = new Atraccion("Prueba 2", TipoAtraccion.AVENTURA, 12, 2, 15);
		Atraccion atracc3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 10, 3, 35);

		PromocionAxB promoAxB = new PromocionAxB("Promo AxB", TipoAtraccion.AVENTURA, atracc, atracc2, atracc3);

		user.aceptarSugerencia(promoAxB);

		assertEquals(24, atracc.getCupo());
		assertEquals(14, atracc2.getCupo());
		assertEquals(34, atracc3.getCupo());
		assertEquals(14, promoAxB.getCupo());
	}

}