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
public class DefaultLoteGuiaNodeHolderFactory extends
		BaseNodeHolderFactory<LoteGuia, Object, Node> {

	private static final String LOTE_GUIA_XPATH = "mensagemTISS/prestadorParaOperadora/loteGuias";

	@Override
	public NodeHolder<LoteGuia, Object, Node> create(NodeHolder<Object, ?, Node> parent)
			throws Exception {

		NodeHolder<LoteGuia, Object, Node> holder = parent.set(LOTE_GUIA_XPATH,
				new NodeConverter<LoteGuia, Object, Node>() {
					@Override
					public LoteGuia convert(
							NodeHolder<LoteGuia, Object, Node> holder, Object parent,
							NodeConverterUnmarshaller<Node> unmarshaller)
							throws Exception {

						return new LoteGuia(holder.getDOMNode().getNodeName());
					}
				});

		new DefaultGuiaTissNodeHolderFactory().create(holder);

		return holder;
	}
}
