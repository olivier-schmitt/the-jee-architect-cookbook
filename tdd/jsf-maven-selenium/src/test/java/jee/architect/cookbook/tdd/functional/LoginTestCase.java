/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.architect.cookbook.tdd.functional;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import junit.framework.TestCase;

/**
 *
 * @author oschmitt
 */
public class LoginTestCase extends TestCase {

    private Selenium selenium;

    protected void setUp() throws Exception {

        this.selenium = new DefaultSelenium("localhost", 4444, "*firefox",
                "http://localhost:8080/jsf-maven-selenium/faces/index.jspx") {

            public void open(String url) {
                commandProcessor.doCommand("open", new String[]{url, "true"});
            }
        };
        selenium.start();
    }

    protected void tearDown() {
        this.selenium.stop();
        this.selenium = null;
    }

    public void testLogin() {
        this.selenium.open("http://localhost:8080/jsf-maven-selenium/faces/index.jspx");
        this.selenium.waitForPageToLoad("5000");

        // Write assertions

        this.selenium.type(buildXPathForIdSelector("login"), "admin");
        this.selenium.type(buildXPathForIdSelector("passwd"), "12345678");
        this.selenium.click(buildXPathForIdSelector("log-in"));
        this.selenium.waitForPageToLoad("5000");

        // Write assertions
        
    }

    protected static String buildXPathForIdSelector(String relativeId) {

        StringBuilder xPathSelector = new StringBuilder("xpath=//*[contains(@id,'");
        xPathSelector.append(relativeId);
        xPathSelector.append("')]");
        return xPathSelector.toString();
    }
}
