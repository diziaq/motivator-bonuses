package se.fastdev.portal.motivator.bonuses.toolbox.capture;

final class HelpCapture {

  /**
   * @return string with linux end lines
   */
  public static String normalLineBreaks(String string) {
    return string.replaceAll("\\r\\n?", "\n");
  }
}
