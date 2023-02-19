package com.getcapacitor;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dev.duma.capacitor.sunmiuhf.StrTools;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void strtools() throws Exception {
        assertArrayEquals(
            new byte[]{0, -1},
            StrTools.hexStrToByteArray("00FF")
        );
        assertEquals(
            "00FF",
            StrTools.byteArrayToHexStr(new byte[]{0, -1})
        );
        assertArrayEquals(
            new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
            StrTools.hexStrToByteArray("000102030405060708090A0B0C0D0E0F")
        );
        assertEquals(
            "000102030405060708090A0B0C0D0E0F",
            StrTools.byteArrayToHexStr(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
        );
        assertArrayEquals(
            new byte[]{0, -1},
            StrTools.hexStrToByteArray("00 FF")
        );
        assertEquals(
            "00 FF",
            StrTools.byteArrayToHexStr(new byte[]{0, -1}, true)
        );
        assertArrayEquals(
            new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
            StrTools.hexStrToByteArray("00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F")
        );
        assertEquals(
            "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F",
            StrTools.byteArrayToHexStr(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, true)
        );

        assertNull(StrTools.hexStrToByteArray("00F"));

        assertEquals(
            "00FF",
            StrTools.normalizeHexStr("00FF")
        );

        assertEquals(
            "00FF",
            StrTools.normalizeHexStr("00 FF")
        );

        assertEquals(
            "00FF",
            StrTools.normalizeHexStr(new byte[]{0, -1})
        );

        assertEquals(
            "00 FF",
            StrTools.normalizeHexStr("00FF", true)
        );

        assertEquals(
            "00 FF",
            StrTools.normalizeHexStr("00 FF", true)
        );

        assertEquals(
            "00 FF",
            StrTools.normalizeHexStr(new byte[]{0, -1}, true)
        );
    }
}
