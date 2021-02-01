package se.fastdev.portal.motivator.bonuses.toolbox.capture.impl;

import java.nio.file.Path;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;

public final class ConstructionMold {

  private ConstructionMold() {
    // namespace
  }

  public static StringCapture makeStringCaptureFromLocalResource(String rootPath) {
    return new StringCaptureFromLocalResource(Path.of(rootPath));
  }
}
