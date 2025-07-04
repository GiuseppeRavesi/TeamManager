/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

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
public class AmichevoleTest {
    
    public AmichevoleTest() {
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
     * Test of getSquadraAvversaria method, of class Amichevole.
     */
    @Test
    public void testGetSquadraAvversaria() {
        System.out.println("getSquadraAvversaria");
        Amichevole instance = null;
        String expResult = "";
        String result = instance.getSquadraAvversaria();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSquadraAvversaria method, of class Amichevole.
     */
    @Test
    public void testSetSquadraAvversaria() {
        System.out.println("setSquadraAvversaria");
        String squadraAvversaria = "";
        Amichevole instance = null;
        instance.setSquadraAvversaria(squadraAvversaria);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Amichevole.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Amichevole instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
