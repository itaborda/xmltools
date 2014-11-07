/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.d4n.oxm.client;

/**
 *
 * @author isaac
 */
public class Valueable {

    private String value;

    public Valueable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" + "value=" + value + '}';
    }
}
