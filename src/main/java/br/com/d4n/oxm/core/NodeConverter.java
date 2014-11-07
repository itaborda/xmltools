/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

/**
 * 
 * @author isaac
 */
public interface NodeConverter<T, P> {

	T convert(NodeHolder<T, P> nodeHolder, P parent, NodeConverterUnmarshaller unmarshaller) throws Exception;
}
