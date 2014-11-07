/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client.parser;

import java.io.InputStream;

import org.w3c.dom.Node;

import br.com.d4n.oxm.client.LoteGuia;
import br.com.d4n.oxm.client.parser.factory.DefaultLoteGuiaNodeHolderFactory;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.W3CNodeHolderBuilderFactory;
import br.com.d4n.oxm.core.helper.NodeHolderFactory;
import br.com.d4n.oxm.core.helper.NodeHolderFactoryDecorator;
import br.com.d4n.oxm.core.helper.NulllNodeHolderFactory;
import br.com.d4n.oxm.core.helper.RootNodeHolderFactory;

/**
 * 
 * @author isaac
 */
public class LoteParserV1 implements
		NodeHolderFactoryDecorator<LoteGuia, Object, NulllNodeHolderFactory<LoteGuia, Object>> {

	NodeHolderFactory<LoteGuia, Object> factory = new DefaultLoteGuiaNodeHolderFactory();

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(NulllNodeHolderFactory<LoteGuia, Object> decorated, InputStream is)
			throws Exception {

		@SuppressWarnings("unchecked")
		NodeHolder<LoteGuia, Object> lote = factory.create(RootNodeHolderFactory
				.getRoot(new W3CNodeHolderBuilderFactory().newBuilder(is)));

		return lote;
	}

	@Override
	public NodeHolder<LoteGuia, Object> createHolder(InputStream is) throws Exception {

		return this.createHolder(new NulllNodeHolderFactory<LoteGuia, Object>(), is);
	}
}