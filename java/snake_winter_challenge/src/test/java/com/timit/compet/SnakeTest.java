package com.timit.compet;

import java.util.*;
import org.junit.jupiter.api.Test;

class SnakeTest {

  @Test
  void isAvailable() {

    /*
    Soit le cas suivant
    	H head
    	T tail
    	P piece :
    	Sans garde fou, le H monte pour attraper la piece
    	il se bloque tout seul.

     P
    ###
    # #
    #H#
      T
    */

    Snake s;
    Point head;
    Point[] parts;
    Point tail;
    head = new Point(0, 0);
    parts = new Point[] {new Point(0, 1), new Point(0, 2)};
    tail = new Point(0, 3);
    s = new Snake(1, true, head, parts, tail);
  }
}
