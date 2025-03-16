package de.jeromewolff.jnet.common.packet;

import de.jeromewolff.jnet.common.buffer.ExtendedByteBuf;

public interface Packet {
  void write(ExtendedByteBuf byteBuf);

  void read(ExtendedByteBuf byteBuf);
}
