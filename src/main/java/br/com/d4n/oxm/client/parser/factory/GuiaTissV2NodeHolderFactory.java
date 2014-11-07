/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser.factory;

import org.w3c.dom.Node;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.helper.BaseNodeHolderFactory;

/**
 * 
 * @author isaac
 */
public class GuiaTissV2NodeHolderFactory extends
		BaseNodeHolderFactory<LoteGuia.GuiaTISS, LoteGuia> {

	private static final String GUIA_TISS_PATH = "novoPathDoGuiaTISS";

	@Override
	public NodeHolder<LoteGuia.GuiaTISS, LoteGuia> create(
			NodeHolder<LoteGuia, ?> parent) throws Exception {

		NodeHolder<LoteGuia.GuiaTISS, LoteGuia> holder = parent.set(
				GUIA_TISS_PATH, new NodeConverter<LoteGuia.GuiaTISS, LoteGuia>() {
					@Override
					public LoteGuia.GuiaTISS convert(
							NodeHolder<LoteGuia.GuiaTISS, LoteGuia> holder,
							LoteGuia parent, NodeConverterUnmarshaller unmarshaller)
							throws Exception {

						LoteGuia.GuiaTISS guiaTISS = new LoteGuia.GuiaTISS(
								holder.getNode().getNodeName());
						parent.setGuiaTISS(guiaTISS);

						return guiaTISS;
					}
				});

		new DefaultGuiaDadosNodeHolderFactory().create(holder);
		new DefaultGuiaProcedimentoNodeHolderFactory().create(holder);

		return holder;
	}
}
