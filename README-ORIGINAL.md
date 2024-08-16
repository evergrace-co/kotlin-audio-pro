# KotlinAudioPro

KotlinAudioPro is an Android audio player written in Kotlin, making it simpler to work with audio playback from streams and files.

## Example

To see the audio player in action, run the example project!
To run the example project, clone the repo, then open in Android Studio.
Choose "kotlin-audio-pro-example" in the run target and run it in a simulator
(or on an actual device).

## Requirements

minSDK 21

## Installation

### Gradle

```gradle
implementation 'com.github.evergrace-co:kotlinaudio:v2.0.0'
```

## Usage

### AudioPlayer

To get started playing some audio:

```swift
let player = AudioPlayer()
let audioItem = DefaultAudioItem(audioUrl: "someUrl", type: MediaType.DEFAULT)
player.load(item: audioItem, playWhenReady: true) // Load the item and start playing when the player is ready.
```

To listen for events in the `AudioPlayer`, subscribe to events found in the `event` property of the `AudioPlayer`.
To subscribe to an event:

```kotlin
// jetpack compose
val state = player.event.stateChange.collectAsState(initial = AudioPlayerState.IDLE)

// normal
player.event.stateChange.collect {}
```

#### QueuedAudioPlayer

The `QueuedAudioPlayer` is a subclass of `AudioPlayer` that maintains a queue of audio tracks.

```swift
let player = QueuedAudioPlayer()
let audioItem = DefaultAudioItem(audioUrl: "someUrl", type: MediaType.DEFAULT)
player.add(item: audioItem, playWhenReady: true) // Since this is the first item, we can supply playWhenReady: true to immedietaly start playing when the item is loaded.
```

When a track is done playing, the player will load the next track and update the queue.

##### Navigating the queue

All `AudioItem`s are stored in either `previousItems` or `nextItems`, which refers to items that come prior to the `currentItem` and after, respectively. The queue is navigated with:

```swift
player.next() // Increments the queue, and loads the next item.
player.previous() // Decrements the queue, and loads the previous item.
player.jumpToItem(index:) // Jumps to a certain item and loads that item.
```

##### Manipulating the queue

```swift
 player.remove(index:) // Remove a specific item from the queue.
 player.removeUpcomingItems() // Remove all items in nextItems.
```

## License

KotinAudio is available under the MIT license. See the LICENSE file for more info.
