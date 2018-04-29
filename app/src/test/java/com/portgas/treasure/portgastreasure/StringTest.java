package com.portgas.treasure.portgastreasure;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringTest {

  @Test
  public void testStringHashValue() {
    int hashAa = "Aa".hashCode();  // = 2112
    int hashBB = "BB".hashCode();  // = 2112
    System.out.println("hash: hashAa = " + hashAa + ",hashBB = " + hashBB);
  }

}