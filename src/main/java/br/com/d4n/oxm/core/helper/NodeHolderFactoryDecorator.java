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
public interface NodeHolderFactoryDecorator<T, P, F extends NodeHolderFactoryDecorator<T, P, ?>> {

	NodeHolder<T, P> createHolder(F factory, InputStream is) throws Exception;

	NodeHolder<T, P> createHolder(InputStream is) throws Exception;

}