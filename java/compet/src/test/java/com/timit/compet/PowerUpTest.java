package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PowerUpTest {

  private final InputStream systemIn = System.in;

  private ByteArrayInputStream testIn;

  private void provideInput(String... data) {
    StringBuilder sb = new StringBuilder();
    for (String s : data) {
      sb.append(s);
      sb.append("\n");
    }
    testIn = new ByteArrayInputStream(sb.toString().getBytes());
    System.setIn(testIn);
  }

  @AfterEach
  public void restoreSystemInputOutput() {
    System.setIn(systemIn);
  }

  @Test
  void builds() {
    provideInput("2", "3 4", "5 6");
    Scanner in = new Scanner(System.in);
    PowerUp[] ups = PowerUp.builds(in);
    assertEquals(ups[0], new Point(3, 4));
    assertEquals(ups[1], new Point(5, 6));
    in.close();
  }
}
