/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core.helper;

import java.io.InputStream;

import br.com.d4n.oxm.core.NodeConverter;
import br.com.d4n.oxm.core.NodeConverterUnmarshaller;
import br.com.d4n.oxm.core.NodeHolder;
import br.com.d4n.oxm.core.NodeHolderBuilder;
import br.com.d4n.oxm.core.NodeHolderBuilderFactory;

/**
 * 
 * @author isaac
 */
public class RootNodeHolderFactory {

	public static <N> NodeHolder<Object, Object> getRoot(NodeHolderBuilderFactory builderFactory, InputStream is) {

		NodeHolderBuilder builder;
		try {
			builder = builderFactory.newBuilder(is);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return getRoot(builder);
	}

	public static NodeHolder<Object, Object> getRoot(NodeHolderBuilder builder) {

		return builder.createRoot(new NodeConverter<Object, Object>() {
			@Override
			public Object convert(NodeHolder<Object, Object> holder, Object parent,
					NodeConverterUnmarshaller unmarshaller) throws Exception {
				return null;
			}
		});
	}
}
