package tierraMedia;

import static org.junit.Assert.*;

import org.junit.Test;

public class PromoPorcentualTest {

	@Test
	public void queCalculaElCostoEnBaseAlDescuentoPorcentual() {
		Atraccion atracc = new Atraccion("Prueba 1", TipoAtraccion.AVENTURA, 10, 5, 25);
		Atraccion atracc2 = new Atraccion("Prueba 2", TipoAtraccion.AVENTURA, 15, 2, 15);

		PromoPorcentual promoP = new PromoPorcentual("Promo AxB", TipoAtraccion.AVENTURA, atracc, atracc2, 20);

		assertEquals(20, promoP.getCosto());
	}

}
