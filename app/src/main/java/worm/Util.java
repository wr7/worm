package worm;

public class Util {
  /**
   * Determinately generate a psuedo-random integer based on a non-random input value.
  */
  public static int randomInt(int input) {
    input ^= (input << 21);
    input ^= (input >>> 35);
    input ^= (input << 4);

    return input;
  }

  /**
   * Determinately generate a psuedo-random integer within a specified range [min, max].
   * based on a non-random input value.
  */
  public static int randomInt(int input, int min, int max) {
    if(input == Integer.MIN_VALUE) {
      input += 1;
    }

    return Math.abs(input) % (max - min + 1) + min;
  }

  /**
   * Determinately generate a psuedo-random integer within a specified range [min, max]
   * based on two non-random input value.
  */
  public static int randomInt(int input1, int input2, int min, int max) {
    int pass1 = randomInt(input1);
    return randomInt(pass1 + input2, min, max);
  }
}
