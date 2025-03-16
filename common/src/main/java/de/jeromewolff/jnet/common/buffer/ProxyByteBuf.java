package de.jeromewolff.jnet.common.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public class ProxyByteBuf extends ByteBuf {
  @Accessors(fluent = true)
  @Getter(value = AccessLevel.PROTECTED)
  private final ByteBuf byteBuf;

  public ProxyByteBuf(ByteBuf byteBuf) {
    this.byteBuf = byteBuf;
  }

  @Override
  public int capacity() {
    return this.byteBuf.capacity();
  }

  @Override
  public ByteBuf capacity(int i) {
    return this.byteBuf.capacity(i);
  }

  @Override
  public int maxCapacity() {
    return this.byteBuf.maxCapacity();
  }

  @Override
  public ByteBufAllocator alloc() {
    return this.byteBuf.alloc();
  }

  @Override
  @Deprecated
  public ByteOrder order() {
    return this.byteBuf.order();
  }

  @Override
  @Deprecated
  public ByteBuf order(ByteOrder byteOrder) {
    return this.byteBuf.order(byteOrder);
  }

  @Override
  public ByteBuf unwrap() {
    return this.byteBuf.unwrap();
  }

  @Override
  public boolean isDirect() {
    return this.byteBuf.isDirect();
  }

  @Override
  public boolean isReadOnly() {
    return this.byteBuf.isReadOnly();
  }

  @Override
  public ByteBuf asReadOnly() {
    return this.byteBuf.asReadOnly();
  }

  @Override
  public int readerIndex() {
    return this.byteBuf.readerIndex();
  }

  @Override
  public ByteBuf readerIndex(int i) {
    return this.byteBuf.readerIndex(i);
  }

  @Override
  public int writerIndex() {
    return this.byteBuf.writerIndex();
  }

  @Override
  public ByteBuf writerIndex(int i) {
    return this.byteBuf.writerIndex(i);
  }

  @Override
  public ByteBuf setIndex(int i, int i1) {
    return this.byteBuf.setIndex(i, i1);
  }

  @Override
  public int readableBytes() {
    return this.byteBuf.readableBytes();
  }

  @Override
  public int writableBytes() {
    return this.byteBuf.writableBytes();
  }

  @Override
  public int maxWritableBytes() {
    return this.byteBuf.maxWritableBytes();
  }

  @Override
  public boolean isReadable() {
    return this.byteBuf.isReadable();
  }

  @Override
  public boolean isReadable(int i) {
    return this.byteBuf.isReadable(i);
  }

  @Override
  public boolean isWritable() {
    return this.byteBuf.isWritable();
  }

  @Override
  public boolean isWritable(int i) {
    return this.byteBuf.isWritable(i);
  }

  @Override
  public ByteBuf clear() {
    return this.byteBuf.clear();
  }

  @Override
  public ByteBuf markReaderIndex() {
    return this.byteBuf.markReaderIndex();
  }

  @Override
  public ByteBuf resetReaderIndex() {
    return this.byteBuf.resetReaderIndex();
  }

  @Override
  public ByteBuf markWriterIndex() {
    return this.byteBuf.markWriterIndex();
  }

  @Override
  public ByteBuf resetWriterIndex() {
    return this.byteBuf.resetWriterIndex();
  }

  @Override
  public ByteBuf discardReadBytes() {
    return this.byteBuf.discardReadBytes();
  }

  @Override
  public ByteBuf discardSomeReadBytes() {
    return this.byteBuf.discardSomeReadBytes();
  }

  @Override
  public ByteBuf ensureWritable(int i) {
    return this.byteBuf.ensureWritable(i);
  }

  @Override
  public int ensureWritable(int i, boolean b) {
    return this.byteBuf.ensureWritable(i, b);
  }

  @Override
  public boolean getBoolean(int i) {
    return this.byteBuf.getBoolean(i);
  }

  @Override
  public byte getByte(int i) {
    return this.byteBuf.getByte(i);
  }

  @Override
  public short getUnsignedByte(int i) {
    return this.byteBuf.getUnsignedByte(i);
  }

  @Override
  public short getShort(int i) {
    return this.byteBuf.getShort(i);
  }

  @Override
  public short getShortLE(int i) {
    return this.byteBuf.getShortLE(i);
  }

  @Override
  public int getUnsignedShort(int i) {
    return this.byteBuf.getUnsignedShort(i);
  }

  @Override
  public int getUnsignedShortLE(int i) {
    return this.byteBuf.getUnsignedShortLE(i);
  }

  @Override
  public int getMedium(int i) {
    return this.byteBuf.getMedium(i);
  }

  @Override
  public int getMediumLE(int i) {
    return this.byteBuf.getMediumLE(i);
  }

  @Override
  public int getUnsignedMedium(int i) {
    return this.byteBuf.getUnsignedMedium(i);
  }

  @Override
  public int getUnsignedMediumLE(int i) {
    return this.byteBuf.getUnsignedMediumLE(i);
  }

  @Override
  public int getInt(int i) {
    return this.byteBuf.getInt(i);
  }

  @Override
  public int getIntLE(int i) {
    return this.byteBuf.getIntLE(i);
  }

  @Override
  public long getUnsignedInt(int i) {
    return this.byteBuf.getUnsignedInt(i);
  }

  @Override
  public long getUnsignedIntLE(int i) {
    return this.byteBuf.getUnsignedIntLE(i);
  }

  @Override
  public long getLong(int i) {
    return this.byteBuf.getLong(i);
  }

  @Override
  public long getLongLE(int i) {
    return this.byteBuf.getLongLE(i);
  }

  @Override
  public char getChar(int i) {
    return this.byteBuf.getChar(i);
  }

  @Override
  public float getFloat(int i) {
    return this.byteBuf.getFloat(i);
  }

  @Override
  public float getFloatLE(int index) {
    return this.byteBuf.getFloatLE(index);
  }

  @Override
  public double getDouble(int i) {
    return this.byteBuf.getDouble(i);
  }

  @Override
  public double getDoubleLE(int index) {
    return this.byteBuf.getDoubleLE(index);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf) {
    return this.byteBuf.getBytes(i, byteBuf);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1) {
    return this.byteBuf.getBytes(i, byteBuf, i1);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
    return this.byteBuf.getBytes(i, byteBuf, i1, i2);
  }

  @Override
  public ByteBuf getBytes(int i, byte[] bytes) {
    return this.byteBuf.getBytes(i, bytes);
  }

  @Override
  public ByteBuf getBytes(int i, byte[] bytes, int i1, int i2) {
    return this.byteBuf.getBytes(i, bytes, i1, i2);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
    return this.byteBuf.getBytes(i, byteBuffer);
  }

  @Override
  public ByteBuf getBytes(int i, OutputStream outputStream, int i1) throws IOException {
    return this.byteBuf.getBytes(i, outputStream, i1);
  }

  @Override
  public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i1) throws IOException {
    return this.byteBuf.getBytes(i, gatheringByteChannel, i1);
  }

  @Override
  public int getBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
    return this.byteBuf.getBytes(i, fileChannel, l, i1);
  }

  @Override
  public CharSequence getCharSequence(int i, int i1, Charset charset) {
    return this.byteBuf.getCharSequence(i, i1, charset);
  }

  @Override
  public ByteBuf setBoolean(int i, boolean b) {
    return this.byteBuf.setBoolean(i, b);
  }

  @Override
  public ByteBuf setByte(int i, int i1) {
    return this.byteBuf.setByte(i, i1);
  }

  @Override
  public ByteBuf setShort(int i, int i1) {
    return this.byteBuf.setShort(i, i1);
  }

  @Override
  public ByteBuf setShortLE(int i, int i1) {
    return this.byteBuf.setShortLE(i, i1);
  }

  @Override
  public ByteBuf setMedium(int i, int i1) {
    return this.byteBuf.setMedium(i, i1);
  }

  @Override
  public ByteBuf setMediumLE(int i, int i1) {
    return this.byteBuf.setMediumLE(i, i1);
  }

  @Override
  public ByteBuf setInt(int i, int i1) {
    return this.byteBuf.setInt(i, i1);
  }

  @Override
  public ByteBuf setIntLE(int i, int i1) {
    return this.byteBuf.setIntLE(i, i1);
  }

  @Override
  public ByteBuf setLong(int i, long l) {
    return this.byteBuf.setLong(i, l);
  }

  @Override
  public ByteBuf setLongLE(int i, long l) {
    return this.byteBuf.setLongLE(i, l);
  }

  @Override
  public ByteBuf setChar(int i, int i1) {
    return this.byteBuf.setChar(i, i1);
  }

  @Override
  public ByteBuf setFloat(int i, float v) {
    return this.byteBuf.setFloat(i, v);
  }

  @Override
  public ByteBuf setFloatLE(int index, float value) {
    return this.byteBuf.setFloatLE(index, value);
  }

  @Override
  public ByteBuf setDouble(int i, double v) {
    return this.byteBuf.setDouble(i, v);
  }

  @Override
  public ByteBuf setDoubleLE(int index, double value) {
    return this.byteBuf.setDoubleLE(index, value);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf) {
    return this.byteBuf.setBytes(i, byteBuf);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1) {
    return this.byteBuf.setBytes(i, byteBuf, i1);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
    return this.byteBuf.setBytes(i, byteBuf, i1, i2);
  }

  @Override
  public ByteBuf setBytes(int i, byte[] bytes) {
    return this.byteBuf.setBytes(i, bytes);
  }

  @Override
  public ByteBuf setBytes(int i, byte[] bytes, int i1, int i2) {
    return this.byteBuf.setBytes(i, bytes, i1, i2);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
    return this.byteBuf.setBytes(i, byteBuffer);
  }

  @Override
  public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
    return this.byteBuf.setBytes(i, inputStream, i1);
  }

  @Override
  public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i1)
    throws IOException {
    return this.byteBuf.setBytes(i, scatteringByteChannel, i1);
  }

  @Override
  public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
    return this.byteBuf.setBytes(i, fileChannel, l, i1);
  }

  @Override
  public ByteBuf setZero(int i, int i1) {
    return this.byteBuf.setZero(i, i1);
  }

  @Override
  public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
    return this.byteBuf.setCharSequence(i, charSequence, charset);
  }

  @Override
  public boolean readBoolean() {
    return this.byteBuf.readBoolean();
  }

  @Override
  public byte readByte() {
    return this.byteBuf.readByte();
  }

  @Override
  public short readUnsignedByte() {
    return this.byteBuf.readUnsignedByte();
  }

  @Override
  public short readShort() {
    return this.byteBuf.readShort();
  }

  @Override
  public short readShortLE() {
    return this.byteBuf.readShortLE();
  }

  @Override
  public int readUnsignedShort() {
    return this.byteBuf.readUnsignedShort();
  }

  @Override
  public int readUnsignedShortLE() {
    return this.byteBuf.readUnsignedShortLE();
  }

  @Override
  public int readMedium() {
    return this.byteBuf.readMedium();
  }

  @Override
  public int readMediumLE() {
    return this.byteBuf.readMediumLE();
  }

  @Override
  public int readUnsignedMedium() {
    return this.byteBuf.readUnsignedMedium();
  }

  @Override
  public int readUnsignedMediumLE() {
    return this.byteBuf.readUnsignedMediumLE();
  }

  @Override
  public int readInt() {
    return this.byteBuf.readInt();
  }

  @Override
  public int readIntLE() {
    return this.byteBuf.readIntLE();
  }

  @Override
  public long readUnsignedInt() {
    return this.byteBuf.readUnsignedInt();
  }

  @Override
  public long readUnsignedIntLE() {
    return this.byteBuf.readUnsignedIntLE();
  }

  @Override
  public long readLong() {
    return this.byteBuf.readLong();
  }

  @Override
  public long readLongLE() {
    return this.byteBuf.readLongLE();
  }

  @Override
  public char readChar() {
    return this.byteBuf.readChar();
  }

  @Override
  public float readFloat() {
    return this.byteBuf.readFloat();
  }

  @Override
  public float readFloatLE() {
    return this.byteBuf.readFloatLE();
  }

  @Override
  public double readDouble() {
    return this.byteBuf.readDouble();
  }

  @Override
  public double readDoubleLE() {
    return this.byteBuf.readDoubleLE();
  }

  @Override
  public ByteBuf readSlice(int i) {
    return this.byteBuf.readSlice(i);
  }

  @Override
  public ByteBuf readRetainedSlice(int i) {
    return this.byteBuf.readRetainedSlice(i);
  }

  @Override
  public ByteBuf readBytes(int i) {
    return this.byteBuf.readBytes(i);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf) {
    return this.byteBuf.readBytes(byteBuf);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf, int i) {
    return this.byteBuf.readBytes(byteBuf, i);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf, int i, int i1) {
    return this.byteBuf.readBytes(byteBuf, i, i1);
  }

  @Override
  public ByteBuf readBytes(byte[] bytes) {
    return this.byteBuf.readBytes(bytes);
  }

  @Override
  public ByteBuf readBytes(byte[] bytes, int i, int i1) {
    return this.byteBuf.readBytes(bytes, i, i1);
  }

  @Override
  public ByteBuf readBytes(ByteBuffer byteBuffer) {
    return this.byteBuf.readBytes(byteBuffer);
  }

  @Override
  public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
    return this.byteBuf.readBytes(outputStream, i);
  }

  @Override
  public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
    return this.byteBuf.readBytes(gatheringByteChannel, i);
  }

  @Override
  public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
    return this.byteBuf.readBytes(fileChannel, l, i);
  }

  @Override
  public CharSequence readCharSequence(int i, Charset charset) {
    return this.byteBuf.readCharSequence(i, charset);
  }

  @Override
  public ByteBuf skipBytes(int i) {
    return this.byteBuf.skipBytes(i);
  }

  @Override
  public ByteBuf writeBoolean(boolean b) {
    return this.byteBuf.writeBoolean(b);
  }

  @Override
  public ByteBuf writeByte(int i) {
    return this.byteBuf.writeByte(i);
  }

  @Override
  public ByteBuf writeShort(int i) {
    return this.byteBuf.writeShort(i);
  }

  @Override
  public ByteBuf writeShortLE(int i) {
    return this.byteBuf.writeShortLE(i);
  }

  @Override
  public ByteBuf writeMedium(int i) {
    return this.byteBuf.writeMedium(i);
  }

  @Override
  public ByteBuf writeMediumLE(int i) {
    return this.byteBuf.writeMediumLE(i);
  }

  @Override
  public ByteBuf writeInt(int i) {
    return this.byteBuf.writeInt(i);
  }

  @Override
  public ByteBuf writeIntLE(int i) {
    return this.byteBuf.writeIntLE(i);
  }

  @Override
  public ByteBuf writeLong(long l) {
    return this.byteBuf.writeLong(l);
  }

  @Override
  public ByteBuf writeLongLE(long l) {
    return this.byteBuf.writeLongLE(l);
  }

  @Override
  public ByteBuf writeChar(int i) {
    return this.byteBuf.writeChar(i);
  }

  @Override
  public ByteBuf writeFloat(float v) {
    return this.byteBuf.writeFloat(v);
  }

  @Override
  public ByteBuf writeFloatLE(float value) {
    return this.byteBuf.writeFloatLE(value);
  }

  @Override
  public ByteBuf writeDouble(double v) {
    return this.byteBuf.writeDouble(v);
  }

  @Override
  public ByteBuf writeDoubleLE(double value) {
    return this.byteBuf.writeDoubleLE(value);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf) {
    return this.byteBuf.writeBytes(byteBuf);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
    return this.byteBuf.writeBytes(byteBuf, i);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i1) {
    return this.byteBuf.writeBytes(byteBuf, i, i1);
  }

  @Override
  public ByteBuf writeBytes(byte[] bytes) {
    return this.byteBuf.writeBytes(bytes);
  }

  @Override
  public ByteBuf writeBytes(byte[] bytes, int i, int i1) {
    return this.byteBuf.writeBytes(bytes, i, i1);
  }

  @Override
  public ByteBuf writeBytes(ByteBuffer byteBuffer) {
    return this.byteBuf.writeBytes(byteBuffer);
  }

  @Override
  public int writeBytes(InputStream inputStream, int i) throws IOException {
    return this.byteBuf.writeBytes(inputStream, i);
  }

  @Override
  public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
    return this.byteBuf.writeBytes(scatteringByteChannel, i);
  }

  @Override
  public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
    return this.byteBuf.writeBytes(fileChannel, l, i);
  }

  @Override
  public ByteBuf writeZero(int i) {
    return this.byteBuf.writeZero(i);
  }

  @Override
  public int writeCharSequence(CharSequence charSequence, Charset charset) {
    return this.byteBuf.writeCharSequence(charSequence, charset);
  }

  @Override
  public int indexOf(int i, int i1, byte b) {
    return this.byteBuf.indexOf(i, i1, b);
  }

  @Override
  public int bytesBefore(byte b) {
    return this.byteBuf.bytesBefore(b);
  }

  @Override
  public int bytesBefore(int i, byte b) {
    return this.byteBuf.bytesBefore(i, b);
  }

  @Override
  public int bytesBefore(int i, int i1, byte b) {
    return this.byteBuf.bytesBefore(i, i1, b);
  }

  @Override
  public int forEachByte(ByteProcessor byteProcessor) {
    return this.byteBuf.forEachByte(byteProcessor);
  }

  @Override
  public int forEachByte(int i, int i1, ByteProcessor byteProcessor) {
    return this.byteBuf.forEachByte(i, i1, byteProcessor);
  }

  @Override
  public int forEachByteDesc(ByteProcessor byteProcessor) {
    return this.byteBuf.forEachByteDesc(byteProcessor);
  }

  @Override
  public int forEachByteDesc(int i, int i1, ByteProcessor byteProcessor) {
    return this.byteBuf.forEachByteDesc(i, i1, byteProcessor);
  }

  @Override
  public ByteBuf copy() {
    return this.byteBuf.copy();
  }

  @Override
  public ByteBuf copy(int i, int i1) {
    return this.byteBuf.copy(i, i1);
  }

  @Override
  public ByteBuf retainedSlice() {
    return this.byteBuf.retainedSlice();
  }

  @Override
  public ByteBuf retainedSlice(int i, int i1) {
    return this.byteBuf.retainedSlice(i, i1);
  }

  @Override
  public ByteBuf slice() {
    return this.byteBuf.slice();
  }

  @Override
  public ByteBuf slice(int i, int i1) {
    return this.byteBuf.slice(i, i1);
  }

  @Override
  public ByteBuf duplicate() {
    return this.byteBuf.duplicate();
  }

  @Override
  public ByteBuf retainedDuplicate() {
    return this.byteBuf.retainedDuplicate();
  }

  @Override
  public int nioBufferCount() {
    return this.byteBuf.nioBufferCount();
  }

  @Override
  public ByteBuffer nioBuffer() {
    return this.byteBuf.nioBuffer();
  }

  @Override
  public ByteBuffer nioBuffer(int i, int i1) {
    return this.byteBuf.nioBuffer(i, i1);
  }

  @Override
  public ByteBuffer internalNioBuffer(int i, int i1) {
    return this.byteBuf.internalNioBuffer(i, i1);
  }

  @Override
  public ByteBuffer[] nioBuffers() {
    return this.byteBuf.nioBuffers();
  }

  @Override
  public ByteBuffer[] nioBuffers(int i, int i1) {
    return this.byteBuf.nioBuffers(i, i1);
  }

  @Override
  public boolean hasArray() {
    return this.byteBuf.hasArray();
  }

  @Override
  public byte[] array() {
    return this.byteBuf.array();
  }

  @Override
  public int arrayOffset() {
    return this.byteBuf.arrayOffset();
  }

  @Override
  public boolean hasMemoryAddress() {
    return this.byteBuf.hasMemoryAddress();
  }

  @Override
  public long memoryAddress() {
    return this.byteBuf.memoryAddress();
  }

  @Override
  public String toString() {
    return this.byteBuf.toString();
  }

  @Override
  public String toString(Charset charset) {
    return this.byteBuf.toString(charset);
  }

  @Override
  public String toString(int i, int i1, Charset charset) {
    return this.byteBuf.toString(i, i1, charset);
  }

  @Override
  public int hashCode() {
    return this.byteBuf.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this.byteBuf.equals(o);
  }

  @Override
  public int compareTo(ByteBuf byteBuf) {
    return this.byteBuf.compareTo(byteBuf);
  }

  @Override
  public ByteBuf retain(int i) {
    return this.byteBuf.retain(i);
  }

  @Override
  public ByteBuf retain() {
    return this.byteBuf.retain();
  }

  @Override
  public ByteBuf touch() {
    return this.byteBuf.touch();
  }

  @Override
  public ByteBuf touch(Object o) {
    return this.byteBuf.touch(o);
  }

  @Override
  public int refCnt() {
    return this.byteBuf.refCnt();
  }

  @Override
  public boolean release() {
    return this.byteBuf.release();
  }

  @Override
  public boolean release(int i) {
    return this.byteBuf.release(i);
  }
}
