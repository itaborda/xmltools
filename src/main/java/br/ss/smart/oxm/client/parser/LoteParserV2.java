/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser;

import java.io.InputStream;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.client.LoteGuia.GuiaTISS.Dados;
import br.ss.smart.oxm.client.parser.factory.DefaultLoteGuiaNodeHolderFactory;
import br.ss.smart.oxm.client.parser.factory.GuiaTissV2NodeHolderFactory;
import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.helper.NodeHolderFactory;
import br.ss.smart.oxm.core.helper.NodeHolderFactoryDecorator;

/**
 * 
 * @author isaac
 */
public class LoteParserV2 implements
		NodeHolderFactoryDecorator<LoteGuia, Object, Node, LoteParserV1> {

	NodeHolderFactory<LoteGuia, Object, Node> f = new DefaultLoteGuiaNodeHolderFactory();

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(LoteParserV1 factory,
			InputStream is) throws Exception {

		NodeHolder<LoteGuia, Object, Node> node = factory.createHolder(is);

		// TROCA DADOS
		this.setDados(node);

		// TROCA GUIATISS
		new GuiaTissV2NodeHolderFactory();

		return node;
	}

	private void setDados(NodeHolder<LoteGuia, Object, Node> node) throws Exception {

		// Neste caso, NodeHolder de Dados vai ter LoteGuia como parent mesmo,
		// na modelagem, Dados sendo filho de GuiaTISS, que é filho de
		// LoteGuia
		String v;
		switch (node.parse(null).getValue()) {
		case "A":
			v = "MODE_A";
			break;
		case "B":
			v = "MODE_B";
			break;
		default:
			v = "MODE_DEF";
			break;
		}

		node.set("/asdasdasd/MODE=" + v,
				new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia, Node>() {

					@Override
					public Dados convert(
							NodeHolder<Dados, LoteGuia, Node> nodeHolder,
							LoteGuia parent, NodeConverterUnmarshaller<Node> unmarshaller)
							throws Exception {

						Dados dados = new Dados(nodeHolder.getDOMNode()
								.getNodeName());
						parent.getGuiaTISS().setDados(dados);

						return dados;
					}
				});

	}

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(InputStream is)
			throws Exception {

		return this.createHolder(new LoteParserV1(), is);
	}

}
