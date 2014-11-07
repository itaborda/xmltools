/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * 
 * @author isaac
 */
public class NodeHolderImpl<T, P> implements NodeHolder<T, P> {

	private final Element node;
	private int index = 0;
	private NodeConverter<T, P> converter;
	private NodeHolder<?, P> next = null;
	private NodeHolder<?, P> previous = null;
	private NodeHolder<P, ?> parent = null;

	private final Map<Integer, NodeHolderImpl<?, T>> childNodes = new TreeMap<>(new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return (o1 == o2) ? 0 : (o1 < o2) ? -1 : 1;
		}
	});

	public NodeHolderImpl(Element node, int index, NodeHolder<P, ?> parent, NodeConverter<T, P> converter) {
		this.node = node;
		this.index = index;
		this.parent = parent;
		this.converter = converter;
	}

	public NodeHolderImpl(Element node, NodeConverter<T, P> parser) {
		this.node = node;
		this.converter = parser;
	}

	@Override
	public Element getNode() {
		return this.node;
	}

	@Override
	public NodeHolder<?, ?> get(int i) {
		return childNodes.get(i);
	}

	@Override
	public Collection<? extends NodeHolder<?, T>> getChildNodes() {
		return childNodes.values();
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public int getPosition() {
		if (this.isRoot()) {
			int i = 0;
			for (NodeHolder<?, ?> child : this.getParent().getChildNodes()) {
				if (child.getIndex() == this.getIndex()) {
					return i;
				}
				i++;
			}
		}
		return 0;
	}

	@Override
	public List<NodeHolder<?, ?>> get(String xpath) {

		List<NodeHolder<?, ?>> list = new ArrayList<>();

		XPath xPath = XPathFactory.newInstance().newXPath();

		NodeList nodeList;
		try {
			nodeList = (NodeList) xPath.compile(xpath).evaluate(this.node, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++) {

				Element newElement = (Element) nodeList.item(i);

				NodeHolder<?, ?> holderRef = get(this, newElement);

				if (holderRef == null) {
					holderRef = new NodeHolderImpl<>(newElement, i, this, null);
				}
				list.add(holderRef);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public <C> NodeHolder<C, T> set(int i, final NodeConverter<C, T> callback) {

		NodeHolder<?, ?> ref = this.get(i);

		Element refElement = ref != null ? ref.getNode() : getNode(this.getNode(), i);

		return set(i, refElement, this, callback);
	}

	private Element getNode(Element n, int index) {

		Node child = n.getFirstChild();
		if (child instanceof Text)
			child = child.getNextSibling();

		for (int i = 1; i < index; i++) {
			child = child.getNextSibling();
			if (child instanceof Text)
				child = child.getNextSibling();
		}

		if (child instanceof Element)
			return (Element) child;

		return n;
	}

	@Override
	public <C> NodeHolder<C, T> set(String xquery, final NodeConverter<C, T> callback) throws Exception {

		XPath xPath = XPathFactory.newInstance().newXPath();

		NodeList nodeList = (NodeList) xPath.compile(xquery).evaluate(this.node, XPathConstants.NODESET);

		if (nodeList.getLength() == 0)
			throw new Exception(new IndexOutOfBoundsException("Not found for xpath '" + xquery + "' in context '"
					+ getXPath(this.node) + "'"));

		NodeHolder<C, T> newNode = null;

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node xNode = nodeList.item(i);
			if (!(xNode instanceof Element)) {
				continue;
			}
			Element element = (Element) xNode;

			System.out.println("result=" + element.toString());

			NodeHolder<?, ?> holderRef = get(this, element);
			int pos = holderRef != null ? holderRef.getIndex() : getPosition(element);

			newNode = set(pos, element, this, callback);
		}
		if (newNode == null) {
			throw new Exception(new IndexOutOfBoundsException("Path found for xpath '" + xquery + "' in context '"
					+ getXPath(this.node) + "' is not a Node"));
		}

		return newNode;
	}

	private String getXPath(Element n) {
		return this.getXPath(n, "");
	}

	private String getXPath(Element n, String path) {

		path += "/" + n.getTagName() + "[@id='" + n.getAttribute("id") + "']";

		Node parent = n.getParentNode();
		if (n.getParentNode() == null || n.equals(n.getParentNode()) || !(parent instanceof Element))
			return path;

		return getXPath((Element) parent, path);
	}

	private <C> NodeHolder<C, T> set(int i, Element refNode, NodeHolder<T, P> parent, final NodeConverter<C, T> parser) {

		NodeHolderImpl<C, T> holderRef = new NodeHolderImpl<>(refNode, i, parent, parser);
		childNodes.put(i, holderRef);
		return holderRef;
	}

	private int getPosition(Element element) {
		int index;
		Node sibling;
		Node n = element;

		index = 0;
		while ((sibling = n.getPreviousSibling()) != null) {
			n = sibling;
			++index;
		}

		return index;
	}

	private NodeHolder<?, ?> get(NodeHolder<?, ?> nodeHolder, Element newNode) {

		NodeHolder<?, ?> result = null;
		for (NodeHolder<?, ?> n : nodeHolder.getChildNodes()) {
			if (n.getNode().equals(newNode)) {
				result = n;
			} else {
				result = get(n, newNode);
			}
			if (result != null) {
				break;
			}
		}
		return result;
	}

	@Override
	public NodeHolder<?, P> next() {
		if (this.next == null) {
			if (!this.isRoot()) {
				Iterator<? extends NodeHolder<?, ?>> iterator = this.getParent().getChildNodes().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					NodeHolder<?, P> child = (NodeHolder<?, P>) iterator.next();
					if (child.getIndex() > this.getIndex()) {
						this.next = child;
					}
				}
			}
		}
		return this.next;
	}

	@Override
	public NodeHolder<?, P> previous() {
		if (this.previous == null) {
			if (!this.isRoot()) {
				Iterator<? extends NodeHolder<?, ?>> iterator = this.getParent().getChildNodes().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					NodeHolder<?, P> child = (NodeHolder<?, P>) iterator.next();
					if (child.getIndex() < this.getIndex()) {
						this.next = child;
					}
				}
			}
		}
		return this.previous;
	}

	@Override
	public NodeHolder<P, ?> getParent() {
		return parent != null ? parent : null;
	}

	@Override
	public boolean isRoot() {
		return parent == null;
	}

	@Override
	public boolean hasNext() {
		return next() != null;
	}

	@Override
	public boolean hasPrevious() {
		return previous() != null;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.node);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		final NodeHolder<T, P> other = (NodeHolder<T, P>) obj;
		if (!Objects.equals(this.getNode(), other)) {
			return false;
		}
		return true;
	}

	private P parsedParent = null;

	@Override
	public T parse(final NodeConverterUnmarshaller unmarshaller) throws Exception {

		T parsed = this.converter.convert(this, this.parsedParent, unmarshaller);

		for (NodeHolderImpl<?, T> child : this.childNodes.values()) {
			child.parsedParent = parsed;
			child.parse(new NodeConverterUnmarshaller() {

				public <R> R unmarshall(Element node) throws Exception {

					return unmarshaller.<R> unmarshall(node);
				}
			});
		}
		return parsed;
	}
}
