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
public class DisponibilitàTest {
    
    private Disponibilità d1;
    private Disponibilità d2;
    private String m1;
    
    @Before
    public void setUp() {
        
        m1 = "Frattura al femore";
        
        d1 = new Disponibilità(1, 1, true, null);
        d2 = new Disponibilità(1, 2, false, m1);
    }
    
    @Test
    public void testMetodoCostruttore(){
        
        //verifico le presenze su d1 e d2
        assertNull(d1.getMotivazione());
        assertEquals(m1,d2.getMotivazione());
        
    }

  
}
