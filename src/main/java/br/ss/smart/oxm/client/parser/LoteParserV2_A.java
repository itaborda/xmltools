/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.helper.NodeHolderFactoryDecorator;

/**
 * 
 * @author isaac
 */
public class LoteParserV2_A implements
		NodeHolderFactoryDecorator<LoteGuia, Object, Node, LoteParserV2> {

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(LoteParserV2 decorator,
			InputStream is) throws Exception {

		NodeHolder<LoteGuia, Object, Node> node = decorator.createHolder(is);

		this.setUnicoProcedimento(node);

		return node;
	}

	private void setUnicoProcedimento(NodeHolder<LoteGuia, Object, Node> node) throws Exception {
		node.set("/asdasdasd/$ocur=3",
				new NodeConverter<LoteGuia.GuiaTISS.Procedimento, LoteGuia, Node>() {

					@Override
					public LoteGuia.GuiaTISS.Procedimento convert(
							NodeHolder<LoteGuia.GuiaTISS.Procedimento, LoteGuia, Node> nodeHolder,
							LoteGuia parent, NodeConverterUnmarshaller<Node> unmarshaller)
							throws Exception {

						List<LoteGuia.GuiaTISS.Procedimento> list = new ArrayList<>();
						LoteGuia.GuiaTISS.Procedimento result = new LoteGuia.GuiaTISS.Procedimento(
								nodeHolder.getDOMNode().getNodeName());
						list.add(result);

						parent.getGuiaTISS().setProcedimentos(list);

						return result;
					}
				});
	}

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(InputStream is)
			throws Exception {

		return this.createHolder(new LoteParserV2(), is);
	}

}
