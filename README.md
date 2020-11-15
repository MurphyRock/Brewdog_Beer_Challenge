# Brewdog_Beer_Challenge_ABAX
Exercise challenge for Abax

For this exercise that consists on creating an app that can pull some data from the punk [API](https://punkapi.com/documentation/v2) and show it, I decided to use the next main technologies:

## Android Architecture Components
I've decided to use Android Architecture Components as wanted to manage the data in an efficient way but also having it available everywhere in an easy way. If the API doesn't return any data because of the internet connection or any other reason, the app will use the data saved in the DB from previous sessions.

Every time the app is initiated from being completly killed, it will try to pull the data from the API and will replace the existing one on the DB.

This process starts on SplashScreen.

Schema: https://developer.android.com/topic/libraries/architecture/images/final-architecture.png

## Retrofit Library
I've used Retrofit to pull the data from the API because of the time that it saves me on teh codding process and because it is considered really efficient

Source: https://square.github.io/retrofit/

## JSON to Data
I didn't use any cool/efficient JSON library to parse the data like [Moshi](https://github.com/square/moshi) because I created the classes in advance without considering it and some of them have relations between them that I wouldn't know how to define for Moshi.

This is something that could be easily improved but I would need to learn how.

## UI/UX
To show data the data I've used the most common way nowadays, which is RecyclerViews. It is an easy and enfficient way to show any list in your app and with the use of adapters you can include almost anything inside every single view.

The data is being shown efficiently as the user can see quickly all the required data of the Beers but the design could be improved easily too. Adding some animations and material design decissions. 

## How ot use
The app is really easy to use. The user just need to open the app and after the quick showing of the SplashScreen, the basic beers information will be shown.
In case some extra data is required, the user need to tap one of the beers. It will be opened a new screen called DetailsScreenFragment with some additional information.
Keep in mind that some data like Hops & Malts of each beer are on reacyclers that can be scrolled horizontally.
