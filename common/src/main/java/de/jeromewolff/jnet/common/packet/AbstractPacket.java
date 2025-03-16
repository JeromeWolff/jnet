package de.jeromewolff.jnet.common.packet;

import de.jeromewolff.jnet.common.buffer.ExtendedByteBuf;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractPacket implements Packet {
  private UUID uniqueId;

  public AbstractPacket() {
    this.uniqueId = UUID.randomUUID();
  }

  @Override
  public void write(ExtendedByteBuf byteBuf) {
    byteBuf.writeUUID(this.uniqueId);
  }

  @Override
  public void read(ExtendedByteBuf byteBuf) {
    this.uniqueId = byteBuf.readUUID();
  }
}
