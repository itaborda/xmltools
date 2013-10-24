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
public interface NodeHolderFactory<T, P, N> {

	NodeHolder<T, P, N> create(
			NodeHolder<P, ?, N> parent,
			@SuppressWarnings("unchecked") NodeHolderFactory<?, T, N>... childFactories)
			throws Exception;
}