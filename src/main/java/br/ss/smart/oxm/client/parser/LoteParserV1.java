/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser;

import java.io.InputStream;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.client.parser.factory.DefaultLoteGuiaNodeHolderFactory;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.W3CNodeHolderBuilderFactory;
import br.ss.smart.oxm.core.helper.NodeHolderFactory;
import br.ss.smart.oxm.core.helper.NodeHolderFactoryDecorator;
import br.ss.smart.oxm.core.helper.NulllNodeHolderFactory;
import br.ss.smart.oxm.core.helper.RootNodeHolderFactory;

/**
 * 
 * @author isaac
 */
public class LoteParserV1
		implements
		NodeHolderFactoryDecorator<LoteGuia, Object, Node, NulllNodeHolderFactory<LoteGuia, Object, Node>> {

	NodeHolderFactory<LoteGuia, Object, Node> factory = new DefaultLoteGuiaNodeHolderFactory();

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(
			NulllNodeHolderFactory<LoteGuia, Object, Node> decorated, InputStream is)
			throws Exception {

		@SuppressWarnings("unchecked")
		NodeHolder<LoteGuia, Object, Node> lote = factory.create(RootNodeHolderFactory
				.getRoot(new W3CNodeHolderBuilderFactory().newBuilder(is)));

		return lote;
	}

	@Override
	public NodeHolder<LoteGuia, Object, Node> createHolder(InputStream is)
			throws Exception {

		return this.createHolder(
				new NulllNodeHolderFactory<LoteGuia, Object, Node>(), is);
	}
}