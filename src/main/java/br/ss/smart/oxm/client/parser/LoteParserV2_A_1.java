/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser;

import java.io.InputStream;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.client.LoteGuia.GuiaTISS.Dados;
import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.helper.NodeHolderFactoryDecorator;

/**
 * 
 * @author isaac
 */
public class LoteParserV2_A_1 implements
		NodeHolderFactoryDecorator<LoteGuia, Object, Node, LoteParserV2_A> {

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(LoteParserV2_A decorated,
			InputStream is) throws Exception {

		NodeHolder<LoteGuia, Object, Node> node = decorated.createHolder(is);

		String v = node.parse(null).getValue();

		node.set("/asdasdasd/ce=" + v,
				new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia, Node>() {

					@Override
					public Dados convert(
							NodeHolder<Dados, LoteGuia, Node> nodeHolder,
							LoteGuia parent, NodeConverterUnmarshaller<Node> unmarshaller)
							throws Exception {

						return new Dados(nodeHolder.getDOMNode().getNodeName());
					}
				});

		return node;
	}

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(InputStream is)
			throws Exception {

		return this.createHolder(new LoteParserV2_A(), is);
	}

}
