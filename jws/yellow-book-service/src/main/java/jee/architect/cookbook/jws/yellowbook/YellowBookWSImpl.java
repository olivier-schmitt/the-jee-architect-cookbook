/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jee.architect.cookbook.jws.yellowbook;

import javax.jws.WebService;

/**
 *
 * @author oschmitt
 */
@WebService(name="YellowBookPortType",serviceName="YellowBookService", portName = "YellowBookPort",wsdlLocation="YellowBookService.wsdl")
public class YellowBookWSImpl implements YellowBook {

    
    public String getPhoneNumber( String firstName, String lastName) {
        return "999999999";
    }

}
