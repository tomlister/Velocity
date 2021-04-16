/*
 * Copyright (C) 2018 Velocity Contributors
 *
 * The Velocity API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the api top-level directory.
 */

package com.velocitypowered.api.event.player;

import com.google.common.base.Preconditions;
import com.velocitypowered.api.proxy.connection.InboundConnection;
import com.velocitypowered.api.util.GameProfile;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * This event is fired after the {@link PreLoginEvent} in
 * order to set up the game profile for the user. This can be used to configure a custom profile for
 * a user, i.e. skin replacement.
 */
public final class GameProfileRequestEvent {

  private final String username;
  private final InboundConnection connection;
  private final GameProfile originalProfile;
  private final boolean onlineMode;
  private @Nullable GameProfile gameProfile;

  /**
   * Creates a new instance.
   * @param connection the connection connecting to the proxy
   * @param originalProfile the original {@link GameProfile} for the user
   * @param onlineMode whether or not the user connected in online or offline mode
   */
  public GameProfileRequestEvent(InboundConnection connection, GameProfile originalProfile,
      boolean onlineMode) {
    this.connection = Preconditions.checkNotNull(connection, "connection");
    this.originalProfile = Preconditions.checkNotNull(originalProfile, "originalProfile");
    this.username = originalProfile.getName();
    this.onlineMode = onlineMode;
  }

  public InboundConnection getConnection() {
    return connection;
  }

  public String getUsername() {
    return username;
  }

  public GameProfile getOriginalProfile() {
    return originalProfile;
  }

  public boolean isOnlineMode() {
    return onlineMode;
  }

  /**
   * Returns the game profile that will be used to initialize the connection with. Should no profile
   * be currently specified, the one generated by the proxy (for offline mode) or retrieved from the
   * Mojang session servers (for online mode) will be returned instead.
   *
   * @return the user's {@link GameProfile}
   */
  public GameProfile getGameProfile() {
    return gameProfile == null ? originalProfile : gameProfile;
  }

  /**
   * Sets the game profile to use for this connection.
   *
   * @param gameProfile the profile for this connection, {@code null} uses the original profile
   */
  public void setGameProfile(@Nullable GameProfile gameProfile) {
    this.gameProfile = gameProfile;
  }

  @Override
  public String toString() {
    return "GameProfileRequestEvent{"
        + "username=" + username
        + ", gameProfile=" + gameProfile
        + "}";
  }


}
