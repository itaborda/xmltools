/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ss.smart.oxm.client.parser.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

import br.ss.smart.oxm.client.LoteGuia;
import br.ss.smart.oxm.client.LoteGuia.GuiaTISS;
import br.ss.smart.oxm.client.LoteGuia.GuiaTISS.Procedimento;
import br.ss.smart.oxm.core.NodeConverter;
import br.ss.smart.oxm.core.NodeConverterUnmarshaller;
import br.ss.smart.oxm.core.NodeHolder;
import br.ss.smart.oxm.core.helper.BaseNodeHolderFactory;

/**
 *
 * @author isaac
 */
public class DefaultGuiaProcedimentoNodeHolderFactory extends BaseNodeHolderFactory<List<LoteGuia.GuiaTISS.Procedimento>, LoteGuia.GuiaTISS, Node> {

    private static final String PATH = "procedimentoExecutado";

    @Override
	public NodeHolder<List<Procedimento>, GuiaTISS, Node> create(
			NodeHolder<GuiaTISS, ?, Node> parent) throws Exception {

        return parent.set(PATH, new NodeConverter<List<LoteGuia.GuiaTISS.Procedimento>, LoteGuia.GuiaTISS, Node>() {
            @Override
            public List<LoteGuia.GuiaTISS.Procedimento> convert(NodeHolder<List<LoteGuia.GuiaTISS.Procedimento>, LoteGuia.GuiaTISS, Node> holder, LoteGuia.GuiaTISS parent, NodeConverterUnmarshaller<Node> unmarshaller) throws Exception {

                final List<LoteGuia.GuiaTISS.Procedimento> list = new ArrayList<>();
                Iterator<? extends NodeHolder<?, List<LoteGuia.GuiaTISS.Procedimento>, Node>> iterator = holder.getChildNodes().iterator();

                int i = 0;
                while (iterator.hasNext()) {
                    @SuppressWarnings("unchecked")
					NodeHolder<Object, List<LoteGuia.GuiaTISS.Procedimento>, Node> child = (NodeHolder<Object, List<LoteGuia.GuiaTISS.Procedimento>, Node>) iterator.next();

                    child.set(i++,
                            new NodeConverter<LoteGuia.GuiaTISS.Procedimento, Object, Node>() {
                        @Override
                        public LoteGuia.GuiaTISS.Procedimento convert(NodeHolder<LoteGuia.GuiaTISS.Procedimento, Object, Node> holder, Object parent, NodeConverterUnmarshaller<Node> unmarshaller) throws Exception {
                        	Procedimento procedimento = new LoteGuia.GuiaTISS.Procedimento(holder.getDOMNode().getNodeName());
                        	list.add(procedimento);
                        	return procedimento;
                        }
                    });
                }
                parent.setProcedimentos(list);
                return list;
            }
        });
    }
}
