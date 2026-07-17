package com.cuoc24h.api.booking;

import java.util.EnumSet;
import java.util.Set;

/** Lifecycle of a customer booking. See docs/05-database-design.md. */
public enum BookingStatus {
    NEW,
    CONTACTING,
    ASSIGNED,
    COMPLETED,
    CANCELLED;

    /** Allowed next states from the current one. Terminal states allow no transitions. */
    public Set<BookingStatus> allowedTransitions() {
        return switch (this) {
            case NEW -> EnumSet.of(CONTACTING, ASSIGNED, CANCELLED);
            case CONTACTING -> EnumSet.of(ASSIGNED, CANCELLED, COMPLETED);
            case ASSIGNED -> EnumSet.of(COMPLETED, CANCELLED, CONTACTING);
            case COMPLETED, CANCELLED -> EnumSet.noneOf(BookingStatus.class);
        };
    }

    public boolean canTransitionTo(BookingStatus target) {
        return this == target || allowedTransitions().contains(target);
    }
}
