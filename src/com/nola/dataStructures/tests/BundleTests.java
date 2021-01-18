package com.nola.dataStructures.tests;

import com.nola.dataStructures.Bundle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BundleTests {
    @Test
    public void ContainsTest(){
        var bundle = new Bundle("BUN01", "Bundle-01", new String[]{"123498-(1)", "837629-(1)"});

        assertTrue(bundle.contains("123498-(1)"));
        assertFalse(bundle.contains("1234598-(1)"));
    }
}
