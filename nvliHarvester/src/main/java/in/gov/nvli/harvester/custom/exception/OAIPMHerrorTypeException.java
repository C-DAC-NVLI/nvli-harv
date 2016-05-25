/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.custom.exception;

/**
 *
 * @author ankit
 */
public class OAIPMHerrorTypeException extends Exception {

    private String message = null;

    public OAIPMHerrorTypeException() {
        super();
    }

    public OAIPMHerrorTypeException(String message) {
        super(message);
        this.message = message;
    }

    public OAIPMHerrorTypeException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
