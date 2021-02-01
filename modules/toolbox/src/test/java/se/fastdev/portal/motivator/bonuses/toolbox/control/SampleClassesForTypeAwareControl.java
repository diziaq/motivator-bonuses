package se.fastdev.portal.motivator.bonuses.toolbox.control;

// -- sample classes for tests of TypeAwareControl --
public class SampleClassesForTypeAwareControl {

  public static class Input {

    // marker for parameterized tests
    public String toString() {
      return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    public String basic() {
      return "basic";
    }
  }

  public static class Input1328562 extends Input {

    public String first() {
      return "first-1328562";
    }
  }

  public static class Input2482658 extends Input {

    public String second() {
      return "second-2482658";
    }
  }

  public static class Input3435646 extends Input1328562 {

    public String first() {
      return "first-3435646";
    }

    public String third() {
      return "third-3435646";
    }
  }

  public static class Input4749832 extends Input {

    public String basic() {
      return "basic-4749832";
    }
  }

  public static class Output {

    final String message;

    public Output(String message) {
      this.message = message;
    }
  }
}
