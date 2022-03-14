# SensorBall

This is a example for Android sensors and custom view. Custome view moves using android sensors 3-axis movement values. Application uses single activity multiple fragment design.



## Libraries

- Jetpack Navigation (Fragment Navigation)
- Jetpack Room (DB)
- Koin (DI)
- Coroutine
- Kotlin Serialization (Json Encode/Decode)



## Screens

### First Screen

First screen has RecyclerView and FloatingActionButton. User can see record with recyclerView and these data fetch from room db. User can record  new record using FloatingActionButton. When press FloatingActionButton move screen fragment.



<img title="" src="file:///Users/utkuyildiz/Library/Application%20Support/marktext/images/2022-03-14-10-46-34-image.png" alt="" width="205">



### Second Screen

Second Screen records movement of custome view. Custome view moves are dependet to android sensors. When device tilt down it goes up, when device tilt up it goes down. This record finish after 10 second and it record with 100ms spaces.



<img title="" src="file:///Users/utkuyildiz/Library/Application%20Support/marktext/images/2022-03-14-10-50-57-image.png" alt="" width="209">
