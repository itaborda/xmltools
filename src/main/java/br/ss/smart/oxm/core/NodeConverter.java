/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

/**
 * 
 * @author isaac
 */
public interface NodeConverter<T, P, Node> {

	T convert(NodeHolder<T, P, Node> nodeHolder, P parent,
			NodeConverterUnmarshaller<Node> unmarshaller) throws Exception;
}
