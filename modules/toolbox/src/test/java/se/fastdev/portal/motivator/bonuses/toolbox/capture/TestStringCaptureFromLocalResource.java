package se.fastdev.portal.motivator.bonuses.toolbox.capture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStringCaptureFromLocalResource {

  @Test
  @DisplayName("should return string with all contents of file")
  public void shouldReadFileContentsWhenFound() {
    var sampleRootPath = "suites/StringCaptureFromLocalResourceTest";
    var sampleContents = "multiline\nfile\ncontents\n";

    var capture = StringCapture.fromLocalResource(sampleRootPath);

    var resultContents = capture.from("nested/dir/multiline_text_sample.txt");

    resultContents = HelpCapture.normalLineBreaks(resultContents);

    assertEquals(sampleContents, resultContents);
  }

  @Test
  @DisplayName("should throw when file not exists")
  public void shouldFailWhenFileNotFound() {
    var sampleRootPath = "suites/StringCaptureFromLocalResourceTest";

    var capture = StringCapture.fromLocalResource(sampleRootPath);

    var exception =
        assertThrows(RuntimeException.class, () -> capture.from("nested/something/nowhere.txt"));

    assertAll(
        () -> assertThat(exception.getMessage(),
                         containsString("Resource is not found: local path")),
        () -> assertThat(exception.getMessage(),
                         containsString("StringCaptureFromLocalResourceTest")),
        () -> assertThat(exception.getMessage(),
                         containsString("nowhere.txt"))
    );
  }
}
