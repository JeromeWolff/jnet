package de.jeromewolff.jnet.common.util;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Files {
  public static File fromByteArray(byte[] bytes) throws IOException {
    Preconditions.checkNotNull(bytes, "bytes");
    var tempFile = File.createTempFile("temp", ".tmp");
    try (var fos = new FileOutputStream(tempFile)) {
      fos.write(bytes);
    }
    return tempFile;
  }

  public static byte[] toByteArray(File file) throws IOException {
    Preconditions.checkNotNull(file, "file");
    var length = (int) file.length();
    var bytes = new byte[length];
    try (var fis = new FileInputStream(file)) {
      fis.read(bytes);
    }
    return bytes;
  }
}
