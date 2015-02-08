/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser;

import java.io.InputStream;

import org.w3c.dom.Node;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.client.LoteGuia.GuiaTISS.Dados;
import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.helper.NodeHolderFactoryDecorator;

/**
 * 
 * @author isaac
 */
public class LoteParserV2_A_1 implements NodeHolderFactoryDecorator<LoteGuia, Object, LoteParserV2_A> {

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(LoteParserV2_A decorated, InputStream is) throws Exception {

		NodeHolder<LoteGuia, Object> node = decorated.createHolder(is);

		String v = node.parse(null).getValue();

/*		node.set("/asdasdasd/ce=" + v, new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia>() {

			@Override
			public Dados convert(NodeHolder<Dados, LoteGuia> nodeHolder, LoteGuia parent,
					NodeConverterUnmarshaller unmarshaller) throws Exception {

				return new Dados(nodeHolder.getNode().getNodeName());
			}
		});*/

		return node;
	}

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(InputStream is) throws Exception {

		return this.createHolder(new LoteParserV2_A(), is);
	}

}
