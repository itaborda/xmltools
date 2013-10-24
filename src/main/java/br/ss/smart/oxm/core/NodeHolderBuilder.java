/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

import nu.xom.Element;

/**
 * 
 * @author isaac
 */
public class NodeHolderBuilder<N> {

	private Element node;

	public NodeHolderBuilder(Element node) {
		this.node = node;
	}

	public <T> NodeHolder<T, T, N> createRoot(
			final NodeConverter<T, T, N> parser) {

		return new NodeHolderImpl<>(node, parser);
	}
}
