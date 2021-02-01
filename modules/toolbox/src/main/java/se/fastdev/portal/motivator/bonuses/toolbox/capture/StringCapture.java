package se.fastdev.portal.motivator.bonuses.toolbox.capture;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.impl.ConstructionMold;

public interface StringCapture {

  String from(String path, Charset charset);

  default String from(String path) {
    return from(path, StandardCharsets.UTF_8);
  }

  static StringCapture fromLocalResource(String rootPath) {
    return ConstructionMold.makeStringCaptureFromLocalResource(rootPath);
  }
}
