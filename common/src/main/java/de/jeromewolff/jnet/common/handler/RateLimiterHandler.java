package de.jeromewolff.jnet.common.handler;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The {@code RateLimiterHandler} class is a Netty handler that limits the rate
 * at which messages are processed in a channel, based on a specified maximum
 * number of messages per second. It delays the processing of incoming messages
 * when they exceed the allowed rate, ensuring that the message flow is controlled
 * and rate-limited.
 *
 * <p>This handler works by calculating the time difference between the current
 * time and the last time a message was processed. If the difference is smaller
 * than the minimum interval (derived from the specified rate), it delays
 * message processing for the required duration.
 *
 * <p>The handler is particularly useful in scenarios where message overloading
 * must be avoided, such as API throttling, resource protection, or limiting
 * data ingestion.
 *
 * @author Jerome Wolff
 * @version 1.0.0
 * @since 1.0.0
 */
public final class RateLimiterHandler extends ChannelInboundHandlerAdapter {
  /**
   * Stores the timestamp of the last message processing time.
   */
  private final AtomicLong lastProcessingTime;
  /**
   * The minimum interval between messages, derived from the maximum allowed
   * messages per second.
   */
  private final long minIntervall;
  /**
   * The {@code TimeUnit} that will be used for time measurements and delays.
   */
  private final TimeUnit timeUnit;

  /**
   * Constructs a new {@code RateLimiterHandler} with a specified maximum
   * message rate and time unit for measurement.
   *
   * @param maxMessagesPerSecond the maximum number of messages allowed per second
   * @param timeUnit             the {@code TimeUnit} to be used for timing calculations
   */
  private RateLimiterHandler(long maxMessagesPerSecond, TimeUnit timeUnit) {
    this.lastProcessingTime = new AtomicLong();
    this.minIntervall = TimeUnit.SECONDS.toMillis(1) / maxMessagesPerSecond;
    this.timeUnit = timeUnit;
  }

  /**
   * Handles the incoming messages, and applies rate limiting by calculating the
   * time difference between consecutive message processing events. If the current
   * message arrives too soon after the last processed message, it is delayed
   * by the necessary time before being processed.
   *
   * @param ctx     the {@code ChannelHandlerContext} that provides access to
   *                the pipeline and associated channel
   * @param message the message to be processed
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object message) {
    var currentTime = System.currentTimeMillis();
    var lastProcessingTime = this.lastProcessingTime.get();
    if (currentTime - lastProcessingTime < this.minIntervall) {
      var delay = this.calculateDelay(currentTime, lastProcessingTime);
      this.delayMessageProcessing(ctx, message, delay);
    } else {
      this.lastProcessingTime.set(currentTime);
      ctx.fireChannelRead(message);
    }
  }

  /**
   * Calculates the delay required to throttle the incoming message processing,
   * ensuring the rate limit is respected.
   *
   * @param currentTime        the current system time in milliseconds
   * @param lastProcessingTime the timestamp of the last processed message
   * @return the delay, in milliseconds, needed to maintain the rate limit
   */
  private long calculateDelay(long currentTime, long lastProcessingTime) {
    return this.timeUnit.toMillis(this.minIntervall) - (currentTime - lastProcessingTime);
  }

  /**
   * Delays the processing of a message by scheduling it to be processed after
   * the calculated delay has elapsed.
   *
   * @param ctx     the {@code ChannelHandlerContext} that provides access to
   *                the pipeline and associated channel
   * @param message the message to be processed after the delay
   * @param delay   the delay in milliseconds before processing the message
   */
  private void delayMessageProcessing(ChannelHandlerContext ctx, Object message, long delay) {
    ctx.executor().schedule(() -> this.processMessage(ctx, message), delay, TimeUnit.MILLISECONDS);
  }

  /**
   * Processes the message by passing it to the next handler in the pipeline.
   * Handles any exceptions that occur during the processing.
   *
   * @param ctx     the {@code ChannelHandlerContext} that provides access to
   *                the pipeline and associated channel
   * @param message the message to be processed
   */
  private void processMessage(ChannelHandlerContext ctx, Object message) {
    try {
      ctx.fireChannelRead(message);
    } catch (Exception ex) {
      ctx.fireExceptionCaught(ex);
    }
  }

  public static RateLimiterHandler create(long maxMessagesPerSecond) {
    return create(maxMessagesPerSecond, TimeUnit.SECONDS);
  }

  public static RateLimiterHandler create(long maxMessagesPerSecond, TimeUnit timeUnit) {
    Preconditions.checkArgument(maxMessagesPerSecond > 0, "maxMessagesPerSecond must be greater than 0");
    Preconditions.checkNotNull(timeUnit, "timeUnit");
    return new RateLimiterHandler(maxMessagesPerSecond, timeUnit);
  }
}
