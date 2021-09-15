package tierraMedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AtraccionTest {

	@Test
	public void notNull() {
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);
		assertNotNull(atracc);
	}

	@Test
	public void comprobarDatos() {
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);
		assertNotNull(atracc);
		assertEquals("Prueba 1", atracc.getNombre());
		assertEquals(TipoAtraccion.AVENTURA, atracc.getTipo());
		assertEquals(10.0, atracc.getCosto(), 0);
		assertEquals(5.0, atracc.getDuracion(), 0);
		assertEquals(25, atracc.getCupo());
	}

	@Test
	public void queDosAtraccionesSonIgualesSiSusNombresSonIguales() {
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);
		Atraccion atracc2 = new Atraccion("Prueba 1", TipoAtraccion.DEGUSTACION, 13, 2, 15);
		Atraccion atracc3 = new Atraccion("Prueba 5", TipoAtraccion.AVENTURA, 10, 3, 35);
		assertEquals(atracc, atracc2);
		assertNotEquals(atracc, atracc3);

	}

	@Test
	public void alComprarUnaAtraccionRestaCupoEn1() {
		Usuario user = new Usuario("Elewen", TipoAtraccion.AVENTURA, 50, 8);
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);

		user.aceptarSugerencia(atracc);
		assertEquals(24, atracc.getCupo());
	}

}
