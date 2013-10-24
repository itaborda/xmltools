/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

import java.util.Collection;
import java.util.List;

import nu.xom.Element;

/**
 * 
 * @author isaac
 */
public interface NodeHolder<T, P, N> {

	T parse(NodeConverterUnmarshaller<N> unmarshaller) throws Exception;

	org.w3c.dom.Element getDOMNode();

	Element getNode();

	NodeHolder<?, ?, N> get(int i);

	List<NodeHolder<?, ?, N>> get(String xpath);

	<C> NodeHolder<C, T, N> set(int i, final NodeConverter<C, T, N> nux);

	<C> NodeHolder<C, T, N> set(String xpath,
			final NodeConverter<C, T, N> callback) throws Exception;

	NodeHolder<?, P, N> next();

	NodeHolder<?, P, N> previous();

	NodeHolder<?, ?, N> getParent();

	Collection<? extends NodeHolder<?, T, N>> getChildNodes();

	boolean hasNext();

	boolean hasPrevious();

	boolean isRoot();

	int getIndex();

	int getPosition();
}
