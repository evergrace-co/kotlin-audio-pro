package co.evergrace.kotlinaudiopro.utils

import co.evergrace.kotlinaudiopro.models.AudioItemTransitionReason
import co.evergrace.kotlinaudiopro.players.BaseAudioPlayer
import co.evergrace.kotlinaudiopro.players.QueuedAudioPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions
import java.time.Duration
import java.util.concurrent.TimeUnit

suspend fun BaseAudioPlayer.seekAndWaitForNextTrackTransition(duration: Long, unit: TimeUnit) {
    seek(duration, unit)
    CoroutineScope(Dispatchers.Default).launch {
        eventually { Assertions.assertEquals(Duration.ofSeconds(duration), Duration.ofMillis(position)) }
        // waits for the item transition after the sync
        launchWithTimeoutSync(this) {
            event.audioItemTransition
                .waitUntil { it == AudioItemTransitionReason.AUTO(0) }
                .collect {}
        }
    }.join()
}

suspend fun QueuedAudioPlayer.nextAndWaitForNextTrackTransition() {
    next()
    CoroutineScope(Dispatchers.Default).launch {
        // waits for the item transition after the sync
        launchWithTimeoutSync(this) {
            event.audioItemTransition
                .waitUntil { it != AudioItemTransitionReason.QUEUE_CHANGED(0) }
                .collect {}
        }
    }.join()
}
