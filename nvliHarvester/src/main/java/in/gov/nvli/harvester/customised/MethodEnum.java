/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.customised;

/**
 *
 * @author ankit
 */
public enum MethodEnum {
    POST("POST"),
    GET("GET");

    private final String text;

    private MethodEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
