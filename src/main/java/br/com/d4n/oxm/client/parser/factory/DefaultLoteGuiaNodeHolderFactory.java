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
public class DefaultLoteGuiaNodeHolderFactory extends BaseNodeHolderFactory<LoteGuia, Object> {

	private static final String LOTE_GUIA_XPATH = "/mensagemTISS/prestadorParaOperadora/loteGuias";

	@Override
	public NodeHolder<LoteGuia, Object> create(NodeHolder<Object, ?> parent) throws Exception {

		NodeHolder<LoteGuia, Object> holder = parent.set(LOTE_GUIA_XPATH, new NodeConverter<LoteGuia, Object>() {
			@Override
			public LoteGuia convert(NodeHolder<LoteGuia, Object> holder, Object parent,
					NodeConverterUnmarshaller unmarshaller) throws Exception {

				return new LoteGuia(holder.getNode().getNodeName());
			}
		});

		new DefaultGuiaTissNodeHolderFactory().create(holder);

		return holder;
	}
}
