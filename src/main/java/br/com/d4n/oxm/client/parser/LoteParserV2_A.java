/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.helper.NodeHolderFactoryDecorator;

/**
 * 
 * @author isaac
 */
public class LoteParserV2_A implements NodeHolderFactoryDecorator<LoteGuia, Object, LoteParserV2> {

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(LoteParserV2 decorator, InputStream is) throws Exception {

		NodeHolder<LoteGuia, Object> node = decorator.createHolder(is);

//		this.setUnicoProcedimento(node);

		return node;
	}

	private void setUnicoProcedimento(NodeHolder<LoteGuia, Object> node) throws Exception {
		node.set("*/*/procedimentosExecutados/procedimentoExecutado[1]",
				new NodeConverter<LoteGuia.GuiaTISS.Procedimento, LoteGuia>() {

					@Override
					public LoteGuia.GuiaTISS.Procedimento convert(
							NodeHolder<LoteGuia.GuiaTISS.Procedimento, LoteGuia> nodeHolder, LoteGuia parent,
							NodeConverterUnmarshaller unmarshaller) throws Exception {

						List<LoteGuia.GuiaTISS.Procedimento> list = new ArrayList<>();
						LoteGuia.GuiaTISS.Procedimento result = new LoteGuia.GuiaTISS.Procedimento(nodeHolder.getNode()
								.getNodeName());
						list.add(result);

						parent.getGuiaTISS().setProcedimentos(list);

						return result;
					}
				});
	}

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(InputStream is) throws Exception {

		return this.createHolder(new LoteParserV2(), is);
	}

}
