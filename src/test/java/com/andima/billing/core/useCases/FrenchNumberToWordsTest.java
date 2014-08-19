package com.andima.billing.core.useCases;

import junit.framework.TestCase;
import org.junit.Test;

public class FrenchNumberToWordsTest extends TestCase {

    @Test
    public void testConvert() throws Exception {
        FrenchNumberToWords convertor = new FrenchNumberToWords();
        assertEquals("zéro", convertor.convert(0));
        assertEquals("neuf", convertor.convert(9));
        assertEquals("dix-neuf", convertor.convert(19));
        assertEquals("vingt et un", convertor.convert(21));
        assertEquals("vingt-huit", convertor.convert(28));
        assertEquals("soixante et onze", convertor.convert(71));
        assertEquals("soixante-douze", convertor.convert(72));
        assertEquals("quatre-vingt", convertor.convert(80));
        assertEquals("quatre-vingt-un", convertor.convert(81));
        assertEquals("quatre-vingt-neuf", convertor.convert(89));
        assertEquals("quatre-vingt-dix", convertor.convert(90));
        assertEquals("quatre-vingt-onze", convertor.convert(91));
        assertEquals("quatre-vingt-dix-sept", convertor.convert(97));
        assertEquals("cent", convertor.convert(100));
        assertEquals("cent un", convertor.convert(101));
        assertEquals("cent dix", convertor.convert(110));
        assertEquals("cent vingt", convertor.convert(120));
        assertEquals("deux cents", convertor.convert(200));
        assertEquals("deux cent un", convertor.convert(201));
        assertEquals("deux cent trente-deux", convertor.convert(232));
        assertEquals("neuf cent quatre-vingt-dix-neuf", convertor.convert(999));
        assertEquals("mille", convertor.convert(1000));
        assertEquals("mille un", convertor.convert(1001));
        assertEquals("dix mille", convertor.convert(10000));
        assertEquals("dix mille un", convertor.convert(10001));
        assertEquals("cent mille", convertor.convert(100000));
        assertEquals("deux millions", convertor.convert(2000000));
        assertEquals("trois milliards", convertor.convert(3000000000L));
        assertEquals("deux milliards cent quarante-sept millions quatre cent quatre-vingt-trois mille six cent " +
                "quarante-sept", convertor.convert(2147483647));
    }
}