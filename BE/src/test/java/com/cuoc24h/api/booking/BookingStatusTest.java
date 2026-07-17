package com.cuoc24h.api.booking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookingStatusTest {

    @Test
    void newCanMoveToContactingAssignedOrCancelled() {
        assertThat(BookingStatus.NEW.canTransitionTo(BookingStatus.CONTACTING)).isTrue();
        assertThat(BookingStatus.NEW.canTransitionTo(BookingStatus.ASSIGNED)).isTrue();
        assertThat(BookingStatus.NEW.canTransitionTo(BookingStatus.CANCELLED)).isTrue();
    }

    @Test
    void terminalStatesAllowNoFurtherTransitions() {
        assertThat(BookingStatus.COMPLETED.canTransitionTo(BookingStatus.NEW)).isFalse();
        assertThat(BookingStatus.CANCELLED.canTransitionTo(BookingStatus.ASSIGNED)).isFalse();
        assertThat(BookingStatus.COMPLETED.allowedTransitions()).isEmpty();
    }

    @Test
    void sameStatusIsAlwaysAllowed() {
        assertThat(BookingStatus.NEW.canTransitionTo(BookingStatus.NEW)).isTrue();
        assertThat(BookingStatus.COMPLETED.canTransitionTo(BookingStatus.COMPLETED)).isTrue();
    }
}
