/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client;

import java.util.List;

/**
 * 
 * @author isaac
 */
public class LoteGuia extends Valueable {

	private GuiaTISS guiaTISS;

	public LoteGuia(String value) {
		super(value);
	}

	public void setGuiaTISS(GuiaTISS node) {
		System.err.println("LoteGuia -> setGuiaTISS: " + node);
		this.guiaTISS = node;
	}

	public GuiaTISS getGuiaTISS() {

		return this.guiaTISS;
	}

	public static class GuiaTISS extends Valueable {

		public GuiaTISS(String value) {
			super(value);
		}

		public void setDados(Dados node) {
			System.err.println("GuiaTISS -> setDados: " + node);
		}

		public void setProcedimentos(List<Procedimento> node) {
			System.err.println("GuiaTISS -> setProcedimentos: " + node);
		}

		public static class Dados extends Valueable {

			public Dados(String value) {
				super(value);
			}
		}

		public static class Procedimento extends Valueable {

			public Procedimento(String value) {
				super(value);
			}
		}
	}
}
