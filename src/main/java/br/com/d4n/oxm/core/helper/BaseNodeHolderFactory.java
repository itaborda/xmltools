/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core.helper;

import br.com.d4n.oxm.core.NodeHolder;

/**
 * 
 * @author isaac
 */
public abstract class BaseNodeHolderFactory<T, P> implements NodeHolderFactory<T, P> {

	@SuppressWarnings("unchecked")
	@Override
	public final NodeHolder<T, P> create(NodeHolder<P, ?> parent, NodeHolderFactory<?, T>... childFactories)
			throws Exception {

		NodeHolder<T, P> holder = this.create(parent);

		for (NodeHolderFactory<?, T> factory : childFactories) {
			factory.create(holder);
		}

		return holder;
	}

	public abstract NodeHolder<T, P> create(NodeHolder<P, ?> parent) throws Exception;

}
