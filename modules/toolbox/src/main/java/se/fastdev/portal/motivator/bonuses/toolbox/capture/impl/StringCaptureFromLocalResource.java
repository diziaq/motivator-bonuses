package se.fastdev.portal.motivator.bonuses.toolbox.capture.impl;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;
import se.fastdev.portal.motivator.bonuses.toolbox.capture.StringCapture;
import se.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException;

final class StringCaptureFromLocalResource implements StringCapture {

  private final Path rootPath;

  /* default */ StringCaptureFromLocalResource(Path rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String from(String path, Charset charset) {
    final var fullPathToResource = rootPath.resolve(path).normalize().toString();
    final var inputStream = ClassLoader.getSystemClassLoader()
                                       .getResourceAsStream(fullPathToResource);

    if (inputStream == null) {
      throw CommonException.normal("Resource is not found: local path " + fullPathToResource);
    }

    try (var scanner = new Scanner(inputStream, charset.name())) {
      return scanner.useDelimiter("\\A").next();
    }
  }
}
