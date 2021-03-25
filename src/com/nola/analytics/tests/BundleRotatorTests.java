package com.nola.analytics.tests;

import org.junit.jupiter.api.Test;

import java.io.*;

public class BundleRotatorTests {
    private InputStream GetClassBundleStream(){
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        try {
            writer.write("#Onkur library per class bundle records\n");
            writer.write("#ClassId = Onkur class id/name. Value = <String>\n");
            writer.write("#BundleIds = Comma separated bundle ids. Value = List<String>\n");
            writer.write("#StudentIds = Comma separated student ids. Value = List<String>\n");
            writer.write("***************************************************************\n");
            writer.write("Class Id:      Chorui\n");
            writer.write("Bundle Ids:    CH-01,CH-02,CH-03,CH-04,CH-05\n");
            writer.write("User Ids:      nameera.rahman,ryaan.ali,ayana.rahim,tairaat.radiah,alisha.mitra,srijon.pal,sadan.roni,sineen.sadi,sunniva.habib,adreesh.dasgupta,totini.tonu\n");
            writer.write("***************************************************************\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        var buffer = memStream.toByteArray();
        try {
            memStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(buffer);
    }
    @Test
    public void ReadClassBundles(){

    }
}
