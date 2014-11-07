/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core.helper;

import java.io.InputStream;

import br.com.d4n.oxm.core.NodeHolder;

/**
 * 
 * @author isaac
 */
public class NulllNodeHolderFactory<T, P> implements NodeHolderFactoryDecorator<T, P, NulllNodeHolderFactory<T, P>> {

	@Override
	public NodeHolder<T, P> createHolder(InputStream is) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public NodeHolder<T, P> createHolder(NulllNodeHolderFactory<T, P> decorated, InputStream is) throws Exception {
		throw new UnsupportedOperationException();
	}

}
