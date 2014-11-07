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
public interface NodeHolderFactory<T, P> {

	NodeHolder<T, P> create(NodeHolder<P, ?> parent,
			@SuppressWarnings("unchecked") NodeHolderFactory<?, T>... childFactories) throws Exception;
}