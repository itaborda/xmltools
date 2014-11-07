/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser.factory;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.client.LoteGuia.GuiaTISS;
import br.com.d4n.oxm.client.LoteGuia.GuiaTISS.Procedimento;
import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.helper.BaseNodeHolderFactory;

/**
 *
 * @author isaac
 */
public class DefaultGuiaProcedimentoNodeHolderFactory extends
		BaseNodeHolderFactory<LoteGuia.GuiaTISS.Procedimento, LoteGuia.GuiaTISS> {

	private static final String PATH = "*/procedimentosExecutados/procedimentoExecutado";

	@Override
	public NodeHolder<Procedimento, GuiaTISS> create(NodeHolder<GuiaTISS, ?> parent) throws Exception {

		return parent.set(PATH, new NodeConverter<LoteGuia.GuiaTISS.Procedimento, LoteGuia.GuiaTISS>() {

			@Override
			public LoteGuia.GuiaTISS.Procedimento convert(
					NodeHolder<LoteGuia.GuiaTISS.Procedimento, LoteGuia.GuiaTISS> holder, LoteGuia.GuiaTISS parent,
					NodeConverterUnmarshaller unmarshaller) throws Exception {

				Procedimento procedimento = new LoteGuia.GuiaTISS.Procedimento(holder.getNode().getNodeName());
				parent.addProcedimento(procedimento);

				return procedimento;
			}
		});
	}
}
