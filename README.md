## Outfit Vault

A Java android mobile app for saving outfit photos and details. 

    AKA KEEP YOUR FITS FRESH WITHOUT THE MENTAL STRAIN OF REMEMBERING EVERY OUTFIT.

OutfitVault allows users to create, edit, and view their own outfits. Users take a photo of their outfit in-app, then provide a description, the season, and a favorite status. Once saved, users can view and edit the outfit, see chart statistics based on the outfits, and search for stores using the in-app map. 

## Project Screen Shot(s)

[ Coming Soon tm ]

## Reflection

This is my first self driven project which I made for my girlfriend and I to use! The goal was to refamiliarize myself with android app development while making something useful for the both of us. I also wanted to challenge myself to try out new libraries and dig into documentation.

I started designing by brainstorming with my girlfriend about what details to save with each outfit. I then asked her what features she'd like which became the basis for my paper prototypes. 

I had never worked with the camera, coded a database, or saved data to local storage before making this app. Coding the database was relatively easy since I had taken advanced databases courses (theory finally coming in handy) but the rest was new territory. To my surprise, I actually quite enjoyed the learning process. 

This app really drove home the importance of refactoring for me. Below was the design process for the Home fragment and Favorites fragment. Throughout this process, functionality was relatively unchanged but clarity and scalability increased dramatically. 

  1. Start out as recycler views on the main activity
  2. Split main activity into home_activity (which displays all outfits) and favorite_activity (which displays all favorited outfits)
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
          - Solution: pay attention to variable/method names and stay consistent!
  - Importance of Simplicity
      - Separating large methods in smaller methods allows for clarity
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
      - Solution: I love constraint layout
          - The once wrongly ignored layout actually had the power to make view sizing on every device relatively equal!

## Obstacles 

My first obstacle was figuring out how to setup dependencies for MP Android Chart. My inexperienc on external libraries coupled with MP Android Chart documentation being unhelpful was a complete disaster. Hiding my google maps API key also took took a couple hours to figure out. Halfway into the project, I realized there was a lot of reused code so I went through an iterative process to remedy this (which was explained in reflections). Two thirds into the project, I added favorites into the DB schema. My one hour of refactoring could easily turn to mutiple days on a larger project. Another issue arose when I tested the app on my phone instead of the emulator. In short, the sizing was very unappealing as my phone is quite large. This was fixed by learning how to properly use constraint layout!
  
## Improvements + Additional thoughts 
 
In light of my database shenanigans, I realized I need to make my code more modular in the future. I also didn't explore automated testing. My next project might be done on test drive development to build good testing habits. I also didn't explore maps and the places SDK to it's full potential. I plan to make a map related app in the near future. 
  
Overall I had a really fun time making this project. I got over my aversion of poorly documented external libraries (just the source code!) and created something a real person is actually using (besides myself)! I'd like to learn how move my databases from local storage to cloud storage next! I'd also like to challenge myself by writing more complex projects. See ya next time!
  
## External libraries used were:
  
[CameraX](https://developer.android.com/jetpack/androidx/releases/camera): Accessing camera
  
[Google maps](https://developers.google.com/maps/documentation): Display map/places
  
[Secrets](https://github.com/google/secrets-gradle-plugin):  Hiding API key
  
[MP Android Chart](https://github.com/PhilJay/MPAndroidChart): Drawing chart
  
[SQLite](https://www.sqlite.org/index.html): Database
