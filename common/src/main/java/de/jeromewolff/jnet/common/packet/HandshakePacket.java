package de.jeromewolff.jnet.common.packet;

import de.jeromewolff.jnet.common.buffer.ExtendedByteBuf;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
public final class HandshakePacket extends AbstractPacket {
  private String clientId;
  private String protocol;
  private int version;

  @Override
  public void write(ExtendedByteBuf byteBuf) {
    byteBuf.writeString(this.clientId);
    byteBuf.writeString(this.protocol);
    byteBuf.writeInt(this.version);
  }

  @Override
  public void read(ExtendedByteBuf byteBuf) {
    this.clientId = byteBuf.readString();
    this.protocol = byteBuf.readString();
    this.version = byteBuf.readInt();
  }
}
