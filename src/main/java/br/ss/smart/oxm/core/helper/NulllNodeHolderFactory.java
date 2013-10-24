/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core.helper;

import java.io.InputStream;

import br.ss.smart.oxm.core.NodeHolder;

/**
 * 
 * @author isaac
 */
public class NulllNodeHolderFactory<T, P, N> implements
		NodeHolderFactoryDecorator<T, P, N, NulllNodeHolderFactory<T, P, N>> {

	@Override
	public NodeHolder<T, P, N> createHolder(InputStream is) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public NodeHolder<T, P, N> createHolder(
			NulllNodeHolderFactory<T, P, N> decorated, InputStream is)
			throws Exception {
		throw new UnsupportedOperationException();
	}

}
