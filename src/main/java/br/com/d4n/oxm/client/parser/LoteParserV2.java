/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser;

import java.io.InputStream;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.client.LoteGuia.GuiaTISS.Dados;
import br.com.d4n.oxm.client.parser.factory.DefaultLoteGuiaNodeHolderFactory;
import br.com.d4n.oxm.client.parser.factory.GuiaTissV2NodeHolderFactory;
import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.helper.NodeHolderFactory;
import br.com.d4n.oxm.core.helper.NodeHolderFactoryDecorator;

/**
 * 
 * @author isaac
 */
public class LoteParserV2 implements NodeHolderFactoryDecorator<LoteGuia, Object, LoteParserV1> {

	NodeHolderFactory<LoteGuia, Object> f = new DefaultLoteGuiaNodeHolderFactory();

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(LoteParserV1 factory, InputStream is) throws Exception {

		NodeHolder<LoteGuia, Object> node = factory.createHolder(is);

		// TROCA DADOS
		this.setDados(node);

		// TROCA GUIATISS
		new GuiaTissV2NodeHolderFactory().create(node);

		return node;
	}

	private void setDados(NodeHolder<LoteGuia, Object> node) throws Exception {

		// Neste caso, NodeHolder de Dados vai ter LoteGuia como parent mesmo,
		// na modelagem, Dados sendo filho de GuiaTISS, que é filho de
		// LoteGuia
		String v;

		String mode = node.get("numeroLote").get(0).getNode().getAttribute("mode");

		switch (mode) {
		case "A":
			v = "modeA";
			break;
		case "B":
			v = "modeB";
			break;
		default:
			v = "modeDef";
			break;
		}

		node.set(v, new NodeConverter<LoteGuia.GuiaTISS.Dados, LoteGuia>() {

			@Override
			public Dados convert(NodeHolder<Dados, LoteGuia> nodeHolder, LoteGuia parent,
					NodeConverterUnmarshaller unmarshaller) throws Exception {

				Dados dados = new Dados(nodeHolder.getNode().getNodeName());
				parent.getGuiaTISS().setDados(dados);

				return dados;
			}
		});

	}

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(InputStream is) throws Exception {

		return this.createHolder(new LoteParserV1(), is);
	}

}
