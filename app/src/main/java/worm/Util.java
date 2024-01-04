package worm;

import java.util.Random;

/**
 * Contains utility methods for generating random numbers.
 */
public final class Util {
  /**
   * Deterministically generate a psuedo-random integer based on a non-random input value.
  */
  public static int randomInt(int input) {
    return new Random(input).nextInt();
  }

  /**
   * Deterministically generate a psuedo-random integer within a specified range [min, max]
   * based on a non-random input value.
  */
  public static int randomInt(int input, int min, int max) {
    if(input == Integer.MIN_VALUE) {
      input += 1;
    }

    return Math.abs(input) % (max - min + 1) + min;
  }

  /**
   * Deterministically generate a psuedo-random integer within a specified range [min, max]
   * based on two non-random input values.
  */
  public static int randomInt(int input1, int input2, int min, int max) {
    int pass1 = randomInt(input1);
    return randomInt(pass1 + input2, min, max);
  }
}
