/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser.factory;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.helper.BaseNodeHolderFactory;

/**
 * 
 * @author isaac
 */
public class DefaultGuiaTissNodeHolderFactory extends
		BaseNodeHolderFactory<LoteGuia.GuiaTISS, LoteGuia, Node> {

	private static final int GUIA_TISS_INDEX = 1;

	@Override
	public NodeHolder<LoteGuia.GuiaTISS, LoteGuia, Node> create(
			NodeHolder<LoteGuia, ?, Node> parent) throws Exception {

		NodeHolder<LoteGuia.GuiaTISS, LoteGuia, Node> holder = parent.set(
				GUIA_TISS_INDEX, new NodeConverter<LoteGuia.GuiaTISS, LoteGuia, Node>() {
					@Override
					public LoteGuia.GuiaTISS convert(
							NodeHolder<LoteGuia.GuiaTISS, LoteGuia, Node> holder,
							LoteGuia parent, NodeConverterUnmarshaller<Node> unmarshaller)
							throws Exception {

						LoteGuia.GuiaTISS guiaTISS = new LoteGuia.GuiaTISS(
								holder.getDOMNode().getNodeName());
						parent.setGuiaTISS(guiaTISS);

						return guiaTISS;
					}
				});

		new DefaultGuiaDadosNodeHolderFactory().create(holder);
		new DefaultGuiaProcedimentoNodeHolderFactory().create(holder);

		return holder;
	}
}
