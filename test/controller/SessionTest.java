/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import model.Utente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enzov
 */
public class SessionTest {
    
    public SessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Session.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Session expResult = null;
        Session result = Session.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class Session.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        Utente utente = null;
        Session instance = null;
        instance.login(utente);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUtenteLoggato method, of class Session.
     */
    @Test
    public void testGetUtenteLoggato() {
        System.out.println("getUtenteLoggato");
        Session instance = null;
        Utente expResult = null;
        Utente result = instance.getUtenteLoggato();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class Session.
     */
    @Test
    public void testLogout() {
        System.out.println("logout");
        Session instance = null;
        instance.logout();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAuthenticated method, of class Session.
     */
    @Test
    public void testIsAuthenticated() {
        System.out.println("isAuthenticated");
        Session instance = null;
        boolean expResult = false;
        boolean result = instance.isAuthenticated();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAllenatore method, of class Session.
     */
    @Test
    public void testIsAllenatore() {
        System.out.println("isAllenatore");
        Session instance = null;
        boolean expResult = false;
        boolean result = instance.isAllenatore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isGiocatore method, of class Session.
     */
    @Test
    public void testIsGiocatore() {
        System.out.println("isGiocatore");
        Session instance = null;
        boolean expResult = false;
        boolean result = instance.isGiocatore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
