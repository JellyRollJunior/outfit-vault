## Outfit Vault

An android mobile app built in Java for saving outfit photos and details. 

    AKA KEEP YOUR FITS FRESH WITHOUT THE MENTAL STRAIN OF REMEMBERING EVERY OUTFIT.

OutfitVault allows users to create, edit, and view their own outfits. OutfitVault uses CameraX to take a photo which is stored in external memory. The user then inputs outfit details associated with the photo which are stored in a local SQLite databse. Once saved, outfit statistics are drawn using MP Android Chart. Oufitvault also contains a map for finding clothing stores. 

## Project Screen Shot(s)

| Chart Fragment | Home Fragment | Favorites Fragment  | Map Fragment |
| ------------- | ------------- | ------------- | ------------- |
| ![OutfitVault - Chart](https://user-images.githubusercontent.com/44827002/143723012-7b60fcf3-758c-4b80-b030-4e969d85f08c.jpg)  | !![OutfitVault - Home](https://user-images.githubusercontent.com/44827002/143722894-902b8d08-a295-48ea-b7b7-e2e4cdc25592.jpg) | ![OutfitVault - Favorites](https://user-images.githubusercontent.com/44827002/143722995-6d5032c7-4e92-4efb-8080-e65aafc5ce43.jpg)  | ![OufitVault - Map](https://user-images.githubusercontent.com/44827002/143723029-5eb64a0c-6568-44b9-8e73-caa779039503.jpg) |

| Outfit View | Edit Outfit | Create Outfit  | Photo Viewfinder |
| ------------- | ------------- | ------------- | ------------- |
| ![OutfitVault - View](https://user-images.githubusercontent.com/44827002/143723060-20a0dbdd-f7eb-40ae-9153-d83f8c5662dd.jpg)  | ![OutfitVault - Edit](https://user-images.githubusercontent.com/44827002/143723062-832e7649-0e12-43c0-a07b-62ec1b791690.jpg) | ![OutfitVault - Create](https://user-images.githubusercontent.com/44827002/143723070-e1d6c4c2-4a6e-4e12-b52a-f5a9ac67ab19.jpg)  | ![OutfitVault - ViewFinder](https://user-images.githubusercontent.com/44827002/143723073-2ecbfd4a-ccff-46f7-a2b8-df59e0ac9e23.jpg)  |

## Reflection

OutfitVault is my first self driven project which I made for my girlfriend and I to use! The goal was to refamiliarize myself with android app development while making something useful for the both of us. I also wanted to challenge myself to try out new libraries and dig into documentation.

I started the design process by brainstorming with my girlfriend about what details to save with each outfit. I then asked her what features she'd like which became the basis for my paper prototypes. Coding the database was relatively easy since I had taken advanced databases courses (theory finally coming in handy). I had never worked with the camera, coded a database, or saved data to local storage before making this app so this was a great learning experience.

One of the biggest lessons Outfitvault taught me was the importance of refactoring. Below was the design process for the Home fragment and Favorites fragment (both perform similar tasks of displaying outfits). Throughout this process, functionality was relatively unchanged but clarity and scalability increased dramatically with each change. 

  1. Start out as recycler views on the main activity
  2. Split main activity into home_activity and favorite_activity
  3. Combine home and favorite into one activity and switch the databases when necessary (led to confusing purpose of each activity)
  4. Split again but into fragments this time so I could navigate between them using a bottom navigation widget. 
  5. These approaches resulted in a lot of shared code so I decided to create an abstract class which holds all the resused code. 

## Key Learnings/Relearnings: 
  - The power of code reuse
      - I painstakingly remade the same activity layout with minor modifications when I could have just used <Include> 
      - Helper classes for commonly used methods
      - Making use of Abstract classes and Inheritance
  - Importance of clarity
      - I was writing spaghetti at the beginning and when I went back to see what was going on, nothing made sense
          - Solution: constant refactoring during and after every commit/push/feature completion
          - Solution: code with the future in mind, you will eventually have to deal with your own code again! Don't make your future self hate you
          - Solution: pay attention to variable/method names and stay consistent!
  - Importance of Simplicity
      - Separating large methods in smaller methods improves clarity
      - Aim for human readable code!
  - Constant testing is key to relieving headaches
      - I would develop a couple features then test complete functionality which resulted in buggy messes
      - quickly learned to test before, during, and after each feature completion
  - Android studios actually wants to help you code
      - warnings and errors shouldn't just be ignored (wow!)
      - Features and hotkeys are extremely useful once time is put into learning them.
  - Basic git workflow
      - issue -> branch -> code -> commit/push -> submit merge request -> merge -> pull -> repeat 
  - Security
      - I had never thought about hiding API keys before making this app so I left my google maps API key public and was alerted of the fact by git guardian.
  - Changing database schema halfway through design is a huge pain (THINK IT THROUGH FIRST!)
  - Apps can look great on one device and wonky on another 
      - Solution: stop hard coding view sizes
          - The layout I wrongly ignored actually had the power to make view sizing on every device relatively equal!

## Obstacles 

Dependencies setup was an issue for MP Android Chart. MP Android Chart's unhelpful documentation and my inexperience resulted in a disaster. Hiding my google maps API key also took took a couple hours to figure out. Halfway into the project, I realized there was a lot of reused code so I went through an iterative process to reduce code (which is explained in reflections). Two thirds into the project, I added favorites into the DB schema which took an hour of coding. This could easily turn to mutiple days on a larger project. During my final round of testing, UI sizing became very unappealing once I tested on my phone (which is larger than the emulator I used). This was fixed by learning how to properly use constraint layout! 
  
## Improvements + Additional thoughts 
 
In light of my database shenanigans, I realized I need to make my code more modular in the future. I also didn't explore automated testing. My next project might be done on test drive development to build good testing habits. I also didn't explore maps and the places SDK to it's full potential. I plan to make a map related app in the near future. Upon the final round of testing on my phone, I also noticed accessing photo files from external storage is quite slow. 
  
Overall I had a really fun month making this project (Oct 25 2021 - Nov 28 2021). I got over my aversion of poorly documented external libraries (just the source code!) and created something a real person is actually using (besides myself)! I'd like to learn how move my databases from local storage to cloud storage next! I'd also like to challenge myself by writing more complex projects. See ya next time!
  
## External libraries:
  
[CameraX](https://developer.android.com/jetpack/androidx/releases/camera): Accessing camera
  
[Google maps](https://developers.google.com/maps/documentation): Display map/places
  
[Secrets](https://github.com/google/secrets-gradle-plugin):  Hiding API key
  
[MP Android Chart](https://github.com/PhilJay/MPAndroidChart): Drawing chart
  
[SQLite](https://www.sqlite.org/index.html): Database
