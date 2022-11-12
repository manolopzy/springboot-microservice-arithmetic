package com.worldexplorer.arithmeticgamification.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This class links a Badge to a User. Contains also a timestamp with the moment in which the user got it.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class BadgeCard implements Serializable{
	/**
	 * marker for redis serialization when use {@link RedisTemplate}
	 */
	@Transient
    private static final long serialVersionUID = -1080910786165679960L;

    private final String userId;
    private final long badgeTimestamp;
    private final Badge badge;

    // Empty constructor for JSON / JPA
    public BadgeCard() {
        this(null, 0, null);
    }

    public BadgeCard(final String userId, final Badge badge) {
        this(userId, System.currentTimeMillis(), badge);
    }

}
