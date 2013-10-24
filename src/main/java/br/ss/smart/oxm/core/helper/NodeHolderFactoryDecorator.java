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
public interface NodeHolderFactoryDecorator<T, P, N, F extends NodeHolderFactoryDecorator<T, P, N, ?>> {

	NodeHolder<T, P, N> createHolder(F factory, InputStream is)
			throws Exception;

	NodeHolder<T, P, N> createHolder(InputStream is) throws Exception;

}