package de.jeromewolff.jnet.common.buffer;

import com.google.common.base.Preconditions;
import de.jeromewolff.jnet.common.util.Files;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExtendedByteBuf {
  private static final Charset UTF_8 = StandardCharsets.UTF_8;
  @Delegate
  private final ByteBuf byteBuf;

  public void writeString(String value, Charset charset) {
    var bytes = value.getBytes(charset);
    this.writeInt(bytes.length);
    this.writeBytes(bytes);
  }

  public String readString(Charset charset) {
    var length = this.readInt();
    var bytes = new byte[length];
    this.readBytes(bytes);
    return new String(bytes, charset);
  }

  public void writeString(String value) {
    this.writeString(value, UTF_8);
  }

  public String readString() {
    return this.readString(UTF_8);
  }

  public void writeStrings(String[] value) {
    this.writeInt(value.length);
    Arrays.stream(value)
      .forEachOrdered(this::writeString);
  }

  public String[] readStrings() {
    var length = this.readInt();
    return IntStream.range(0, length)
      .mapToObj(i -> readString())
      .toArray(String[]::new);
  }

  public void writeStringList(List<String> value) {
    this.writeInt(value.size());
    value.forEach(this::writeString);
  }

  public List<String> readStringList() {
    var length = this.readInt();
    return IntStream.range(0, length)
      .mapToObj(i -> readString())
      .collect(Collectors.toList());
  }

  public void writeUUID(UUID uuid) {
    var mostSignificantBits = uuid.getMostSignificantBits();
    var leastSignificantBits = uuid.getLeastSignificantBits();
    this.writeLong(mostSignificantBits);
    this.writeLong(leastSignificantBits);
  }

  public UUID readUUID() {
    var mostSignificantBits = this.readLong();
    var leastSignificantBits = this.readLong();
    return new UUID(mostSignificantBits, leastSignificantBits);
  }

  public <T extends Enum<T>> void writeEnum(Enum<T> enumClass) {
    var enumName = enumClass.name();
    this.writeString(enumName);
  }

  public <T extends Enum<T>> Enum<T> readEnum(Class<T> enumClass) {
    var enumName = this.readString();
    return Enum.valueOf(enumClass, enumName);
  }

  public void writeBigDecimal(BigDecimal decimal) {
    var decimalString = decimal.toString();
    this.writeString(decimalString);
  }

  public BigDecimal readBigDecimal() {
    var decimalString = this.readString();
    return new BigDecimal(decimalString);
  }

  public void writeBigInteger(BigInteger integer) {
    var bytes = integer.toByteArray();
    this.writeInt(bytes.length);
    this.writeBytes(bytes);
  }

  public BigInteger readBigInteger() {
    var length = this.readInt();
    var bytes = new byte[length];
    this.readBytes(bytes);
    return new BigInteger(bytes);
  }

  public void writeLocalDateTime(LocalDateTime dateTime) {
    var epochSeconds = dateTime.toEpochSecond(ZoneOffset.UTC);
    var nano = dateTime.getNano();
    this.writeLong(epochSeconds);
    this.writeInt(nano);
  }

  public LocalDateTime readLocalDateTime() {
    var epochSeconds = this.readLong();
    var nano = this.readInt();
    return LocalDateTime.ofEpochSecond(epochSeconds, nano, ZoneOffset.UTC);
  }

  public void writeDate(Date date) {
    var time = date.getTime();
    this.writeLong(time);
  }

  public Date readDate() {
    var time = this.readLong();
    return new Date(time);
  }

  public void writeCalendar(Calendar calendar) {
    var timeInMillis = calendar.getTimeInMillis();
    this.writeLong(timeInMillis);
  }

  public Calendar readCalendar() {
    var timeInMillis = this.readLong();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timeInMillis);
    return calendar;
  }

  public void writeDuration(Duration duration) {
    var seconds = duration.getSeconds();
    var nano = duration.getNano();
    this.writeLong(seconds);
    this.writeInt(nano);
  }

  public Duration readDuration() {
    var seconds = this.readLong();
    var nano = this.readInt();
    return Duration.ofSeconds(seconds, nano);
  }

  public void writeInetAddress(InetAddress address) {
    var addressBytes = address.getAddress();
    this.writeInt(addressBytes.length);
    this.writeBytes(addressBytes);
  }

  public InetAddress readInetAddress() {
    var length = this.readInt();
    var addressBytes = new byte[length];
    this.readBytes(addressBytes);
    try {
      return InetAddress.getByAddress(addressBytes);
    } catch (Exception ex) {
      throw new RuntimeException("Failed to read InetAddress from ByteBuf", ex);
    }
  }

  public void writeLocale(Locale locale) {
    var languageTag = locale.toLanguageTag();
    this.writeString(languageTag);
  }

  public Locale readLocale() {
    var languageTag = this.readString();
    return Locale.forLanguageTag(languageTag);
  }

  public void writeCharset(Charset charset) {
    this.writeString(charset.name());
  }

  public Charset readCharset() {
    return Charset.forName(this.readString());
  }

  public void writeFile(File file) throws IOException {
    var bytes = Files.toByteArray(file);
    this.writeInt(bytes.length);
    this.writeBytes(bytes);
  }

  public File readFile() throws IOException {
    var length = this.readInt();
    var bytes = new byte[length];
    this.readBytes(bytes);
    return Files.fromByteArray(bytes);
  }

  public void writeFileInputStream(FileInputStream fis) throws IOException {
    var bytes = new byte[1024];
    int bytesRead;
    while ((bytesRead = fis.read(bytes)) != -1) {
      this.writeBytes(bytes, 0, bytesRead);
    }
  }

  public FileInputStream readFileInputStream() throws IOException {
    var readableBytes = this.readableBytes();
    var bytes = new byte[readableBytes];
    this.readBytes(bytes);
    var file = Files.fromByteArray(bytes);
    return new FileInputStream(file);
  }

  public void writeFileOutputStream(FileOutputStream fos) throws IOException {
    var bytes = new byte[1024];
    int bytesRead;
    while ((bytesRead = this.readableBytes()) != -1) {
      this.readBytes(bytes, 0, bytesRead);
      fos.write(bytes, 0, bytesRead);
    }
  }

  public FileOutputStream readFileOutputStream() throws IOException {
    var file = this.readFile();
    return new FileOutputStream(file);
  }

  public void writePattern(Pattern pattern) {
    var patternString = pattern.pattern();
    var flags = pattern.flags();
    this.writeString(patternString);
    this.writeInt(flags);
  }

  public Pattern readPattern() {
    var regex = this.readString();
    var flags = this.readInt();
    return Pattern.compile(regex, flags);
  }

  private static ExtendedByteBuf of(ByteBuf byteBuf) {
    Preconditions.checkNotNull(byteBuf, "byteBuf");
    return new ExtendedByteBuf(byteBuf);
  }

  public static ExtendedByteBuf create() {
    var byteBuf = Unpooled.buffer();
    return of(byteBuf);
  }

  public static ExtendedByteBuf allocate(int capacity) {
    var wrapped = Unpooled.buffer(capacity);
    return of(wrapped);
  }

  public static ExtendedByteBuf wrap(ByteBuf byteBuf) {
    var wrapped = Unpooled.wrappedBuffer(byteBuf);
    return of(wrapped);
  }
}
