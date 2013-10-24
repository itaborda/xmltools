/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParentNode;
import nu.xom.converters.DOMConverter;
import nux.xom.pool.XOMUtil;
import nux.xom.xquery.XQueryUtil;

/**
 * 
 * @author isaac
 */
public class NodeHolderImpl<T, P, N> implements NodeHolder<T, P, N> {

	private final Element node;
	private int index = 0;
	private NodeConverter<T, P, N> converter;
	private NodeHolder<?, P, N> next = null;
	private NodeHolder<?, P, N> previous = null;
	private NodeHolder<?, ?, N> parent = null;
	private final Map<Integer, NodeHolderImpl<?, T, N>> childNodes = new TreeMap<>(
			new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return (o1 == o2) ? 0 : (o1 < o2) ? -1 : 1;
				}
			});

	public NodeHolderImpl(Element node, int index, NodeHolder<?, ?, N> parent,
			NodeConverter<T, P, N> converter) {
		this.node = node;
		this.index = index;
		this.parent = parent;
		this.converter = converter;
	}

	public NodeHolderImpl(Element node, NodeConverter<T, P, N> parser) {
		this.node = node;
		this.converter = parser;
	}

	@Override
	public Element getNode() {
		return this.node;
	}

	@Override
	public org.w3c.dom.Element getDOMNode() {

		return this.getW3CNome(this.node);
	}

	private org.w3c.dom.Element getW3CNome(Element element) {

		Element elem = (Element) element.copy();

		return DOMConverter.convert(new Document(elem),
				XOMUtil.getDOMImplementation()).getDocumentElement();
	}

	@Override
	public NodeHolder<?, ?, N> get(int i) {
		return childNodes.get(i);
	}

	@Override
	public Collection<? extends NodeHolder<?, T, N>> getChildNodes() {
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
			for (NodeHolder<?, ?, N> child : this.getParent().getChildNodes()) {
				if (child.getIndex() == this.getIndex()) {
					return i;
				}
				i++;
			}
		}
		return 0;
	}

	@Override
	public List<NodeHolder<?, ?, N>> get(String xpath) {

		List<NodeHolder<?, ?, N>> list = new ArrayList<>();

		// nu.xom.Document xDoc = DOMConverter.convert(parse(this.node));
		// find the atom named 'Zinc' in the periodic table:
		nu.xom.Nodes xNodes = XQueryUtil.xquery(this.node, xpath);
		for (int i = 0; i < xNodes.size(); i++) {

			Element newElement = (Element) xNodes.get(i);

			NodeHolder<?, ?, N> nuxRef = get(this, newElement);
			if (nuxRef != null) {
				list.add(nuxRef);
			}
		}
		return list;
	}

	/*
	 * private Document parse(Element node) {
	 * 
	 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	 * factory.setNamespaceAware(true);
	 * 
	 * DocumentBuilder builder; try { builder = factory.newDocumentBuilder();
	 * 
	 * Document oldDocument = node.getOwnerDocument(); Element oldDocElement =
	 * oldDocument.getDocumentElement();
	 * 
	 * Document newDocument = builder.newDocument();
	 * newDocument.appendChild(newDocument.importNode(node, true));
	 * 
	 * Element newDocElement = newDocument.getDocumentElement();
	 * 
	 * // Copy Attributes NamedNodeMap namedNodeMap =
	 * oldDocElement.getAttributes(); for (int x = 0; x <
	 * namedNodeMap.getLength(); x++) { Attr attr = (Attr)
	 * newDocument.importNode(namedNodeMap.item(x), true);
	 * newDocElement.setAttributeNodeNS(attr); }
	 * 
	 * // Element nsAttr = //
	 * oldDocument.createElementNS(oldDocument.getNamespaceURI(), //
	 * oldDocElement.getNodeName());
	 * 
	 * // newDocument.setDocumentURI(oldDocument.getDocumentURI()); //
	 * newDocElement.setPrefix(oldDocElement.getPrefix()); //
	 * newDocElement.appendChild(nsAttr);
	 * 
	 * return newDocument; } catch (ParserConfigurationException ex) { return
	 * node.getOwnerDocument(); } }
	 */

	@Override
	public <C> NodeHolder<C, T, N> set(int i,
			final NodeConverter<C, T, N> callback) {

		NodeHolder<?, ?, N> ref = this.get(i);

		Element refElement = ref != null ? ref.getNode() : getNode(
				this.getNode(), i);

		return set(i, refElement, this, callback);
	}

	private Element getNode(Element n, int index) {

		for (int i = 0; i < n.getChildElements().size(); i++) {
			if (index == i) {
				return (Element) n.getParent().getChild(i);
			}
		}
		return n;
	}

	@Override
	public <C> NodeHolder<C, T, N> set(String xquery,
			final NodeConverter<C, T, N> callback) throws Exception {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			org.w3c.dom.Document newDoc = builder.newDocument();
			Source iSource = new DOMSource(newDoc);

			Configuration config = new Configuration();			
			StaticQueryContext sqc = config.newStaticQueryContext();
			sqc.setSchemaAware(true);
			sqc.setInheritNamespaces(true);

			DynamicQueryContext iDynContext = new DynamicQueryContext(config);
			DocumentInfo iDocInfo = null;

			iDocInfo = sqc.buildDocument(iSource);
			iDynContext.setContextNode(iDocInfo.getRoot());

			// First of all, get document revisions
			List iTitles = null;
			XQueryExpression iQuery = sqc.compileQuery(xquery);
			iTitles = iQuery.evaluate(iDynContext);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// nu.xom.Document xDoc = DOMConverter.convert(parse(this.node));

		// find the atom named 'Zinc' in the periodic table:
		Nodes xquery = XQueryUtil.xquery(this.node, xpath);
		if (xquery.size() == 0)
			throw new Exception(new IndexOutOfBoundsException(
					"Not found for xpath '" + xpath + "' in context '"
							+ getXPath(this.node) + "'"));

		Node xNode = xquery.get(0);
		if (!(xNode instanceof Element)) {
			throw new Exception(new IndexOutOfBoundsException(
					"Found for xpath '" + xpath + "' object '" + xNode
							+ "', type is '" + xNode
							+ "', may be a Element type'"));
		}
		Element newNode = (Element) xquery.get(0);
		System.out.println("result=" + newNode.toXML());

		// Document newElement = DOMConverter.convert(xNode.getDocument(),
		// this.node
		// .getOwnerDocument().getImplementation());

		NodeHolder<?, ?, N> nuxRef = get(this, newNode);
		int i = nuxRef != null ? nuxRef.getIndex() : getPosition(newNode);

		return set(i, newNode, this, callback);
	}

	private String getXPath(Element n) {
		return this.getXPath(n, "");
	}

	private String getXPath(Element n, String path) {

		path += "/" + n.getQualifiedName() + "[@id='" + n.getAttribute("id")
				+ "']";

		ParentNode parent = n.getParent();
		if (n.getParent() == null || n.equals(n.getParent())
				|| !(parent instanceof Element))
			return path;

		return getXPath((Element) parent, path);
	}

	private <C> NodeHolder<C, T, N> set(int i, Element refNode,
			NodeHolder<T, P, N> parent, final NodeConverter<C, T, N> parser) {

		NodeHolderImpl<C, T, N> newNux = new NodeHolderImpl<>(refNode, i,
				parent, parser);
		childNodes.put(i, newNux);
		return newNux;
	}

	private int getPosition(Element n) {
		for (int i = 0; i < n.getParent().getChildCount(); i++) {
			Element child = n.getChildElements().get(i);
			if (child.equals(n)) {
				return i;
			}
			i++;
		}
		return 0;
	}

	private NodeHolder<?, ?, N> get(NodeHolder<?, ?, N> nux, Element newNode) {

		NodeHolder<?, ?, N> result = null;
		for (NodeHolder<?, ?, N> n : nux.getChildNodes()) {
			if (n.getDOMNode().equals(newNode)) {
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
	public NodeHolder<?, P, N> next() {
		if (this.next == null) {
			if (!this.isRoot()) {
				Iterator<? extends NodeHolder<?, ?, N>> iterator = this
						.getParent().getChildNodes().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					NodeHolder<?, P, N> child = (NodeHolder<?, P, N>) iterator
							.next();
					if (child.getIndex() > this.getIndex()) {
						this.next = child;
					}
				}
			}
		}
		return this.next;
	}

	@Override
	public NodeHolder<?, P, N> previous() {
		if (this.previous == null) {
			if (!this.isRoot()) {
				Iterator<? extends NodeHolder<?, ?, N>> iterator = this
						.getParent().getChildNodes().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("unchecked")
					NodeHolder<?, P, N> child = (NodeHolder<?, P, N>) iterator
							.next();
					if (child.getIndex() < this.getIndex()) {
						this.next = child;
					}
				}
			}
		}
		return this.previous;
	}

	@Override
	public NodeHolder<?, ?, N> getParent() {
		return parent != null ? parent : this;
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
		final NodeHolder<T, P, N> other = (NodeHolder<T, P, N>) obj;
		if (!Objects.equals(this.getDOMNode(), other)) {
			return false;
		}
		return true;
	}

	private P parsedParent = null;

	@Override
	public T parse(final NodeConverterUnmarshaller<N> unmarshaller)
			throws Exception {

		T parsed = this.converter
				.convert(this, this.parsedParent, unmarshaller);

		for (NodeHolderImpl<?, T, N> child : this.childNodes.values()) {
			child.parsedParent = parsed;
			child.parse(new NodeConverterUnmarshaller<N>() {

				public <R> R unmarshall(N node) throws Exception {

					return unmarshaller.<R> unmarshall(node);
				}
			});
		}
		return parsed;
	}
}
