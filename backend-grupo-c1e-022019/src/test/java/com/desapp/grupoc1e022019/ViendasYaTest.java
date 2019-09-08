package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.builder.ProviderBuilder;
import com.desapp.grupoc1e022019.model.ViendasYa;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class ViendasYaTest  extends TestCase {
    @Test
    public void testWhenICreateANewViendasYaItHasNoProviders(){
        ViendasYa viendasYa = new ViendasYa();

        assertEquals(viendasYa.getProviders(),new ArrayList<Provider>());
    }

    @Test
    public void testWhenIAddANewProviderThenViendasYaHasOneMoreProvider(){
        ViendasYa viendasYa = new ViendasYa();
        Provider aNewProvider = ProviderBuilder.aProvider().build();

        viendasYa.addProvider(aNewProvider);

        assertEquals(viendasYa.getProviders().size(),1);
    }
}
