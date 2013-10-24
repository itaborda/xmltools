/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core.helper;

import br.ss.smart.oxm.core.NodeHolder;

/**
 * 
 * @author isaac
 */
public abstract class BaseNodeHolderFactory<T, P, N> implements
		NodeHolderFactory<T, P, N> {

	@SuppressWarnings("unchecked")
	@Override
	public final NodeHolder<T, P, N> create(NodeHolder<P, ?, N> parent,
			NodeHolderFactory<?, T, N>... childFactories) throws Exception {

		NodeHolder<T, P, N> holder = this.create(parent);

		for (NodeHolderFactory<?, T, N> factory : childFactories) {
			factory.create(holder);
		}

		return holder;
	}

	public abstract NodeHolder<T, P, N> create(NodeHolder<P, ?, N> parent)
			throws Exception;

}
