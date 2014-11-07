/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

import org.w3c.dom.Element;

/**
 * 
 * @author isaac
 */
public class NodeHolderBuilder {

	private Element node;

	public NodeHolderBuilder(Element node) {
		this.node = node;
	}

	public <T> NodeHolder<T, T> createRoot(final NodeConverter<T, T> parser) {

		return new NodeHolderImpl(node, parser);
	}
}
