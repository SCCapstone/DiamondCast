# DiamondCast
Capstone Computing Project 2021-2022
https://github.com/SCCapstone/DiamondCast/wiki

## Our Project 

The goal of our app is to allow customers to connect with local contractors and vendors in their area.  We plan on doing this through easily allowing users to recommend their go-to contrators to friends and family or search through our network of verified contractors.  We also plan on integrating agent accounts to allow people in the real estate industry to recommend to their own clients.  Our app will allow customers to find, message, and set up appointments with contractors through our app.  We want to streamline the stressful process of selecting someone who you can trust to work on your home.  

## External Requirements

In order to build this project you first have to install:

* [AndroidStudio](https://developer.android.com/studio/install)


## Setup

First download android studios, then open it. Select open project and open this project using the folder "DiamondCastApp" from the cloned github url. 
Go to the AVD Manager located in the top right to download a virtual android. The android device you select will be used as an emulator to run your app.

## Running

To run the application, you must open Android Studio and open the DiamondCastApp project (The DiamondCastApp should be the base directory in your andriod studio project. Once this is done, you can select "build" to make sure you have the latest build and "run" to run the app on an android emulator.

## Deployment

To build our application and create a "release" open the DiamondCastApp in Android Studio and choose the Build > Build Bundle/APK > Build APK from the top menu bar. 
A release can be created by uploading the APK to the appropriate github tag. The APK can be downloaded from the github release and executing this file will install the app on your device.

## Testing

Unit Testing Directory - app/src/test/java/com/example/diamondcastapp

UI Testing Directory - app/src/androidTest/java/com/example/diamondcastapp

## Testing Technology

JUnit - for unit testing

Espresso - for instrumentation testing

## Running Tests

If you have our project open in Android Studio, running the tests is easy:  Simply navigate through the file structure of the app and run the tests by right-clicking on each directory and choosing "run 'Tests in 'com.example...".

Otherwise you can run each set of tests from the command line using:

For Unit Tests:  ./gradlew test 

For Instrumentation Tests: ./gradlew cAT

## Authors

* Frank Habersham frankh@email.sc.edu
* Jackson Trigiani jacktrig@email.sc.edu
* Daniel McKenna mckennd@email.sc.edu
* Justin Cordell cordellj@email.sc.edu
* Jakob Brown jakobwb@email.sc.edu

