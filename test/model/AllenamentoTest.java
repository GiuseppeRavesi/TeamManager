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
public class AllenamentoTest {
    
    public AllenamentoTest() {
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
     * Test of getTipologia method, of class Allenamento.
     */
    @Test
    public void testGetTipologia() {
        System.out.println("getTipologia");
        Allenamento instance = null;
        String expResult = "";
        String result = instance.getTipologia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTipologia method, of class Allenamento.
     */
    @Test
    public void testSetTipologia() {
        System.out.println("setTipologia");
        String tipologia = "";
        Allenamento instance = null;
        instance.setTipologia(tipologia);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNote method, of class Allenamento.
     */
    @Test
    public void testGetNote() {
        System.out.println("getNote");
        Allenamento instance = null;
        String expResult = "";
        String result = instance.getNote();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNote method, of class Allenamento.
     */
    @Test
    public void testSetNote() {
        System.out.println("setNote");
        String note = "";
        Allenamento instance = null;
        instance.setNote(note);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Allenamento.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Allenamento instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
