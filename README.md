# dbs-student-android-app

Assignment Objectives:
1. To develop a mobile application using Android
2. To utilise the components discussed in the lectures to complement the functionality of your
application
3. To run and debug the application and make sure it’s as much error free as possible

Details of assignment brief:

We will work on a mobile application whose capability relies on obtaining data from an API.
You will be given API links that will enable the login functionality for your application. All
various API links should be provided to you during the lecture on the 26th of March. You will
implement the login on your applications, store the session in an SQLite database to make
sure that a user doesn’t have to see the login screen when they are already logged into the
system.

You will also have an API link that will provide you with a list of few various modules, this will
all be dummy data, but you will present this information either using a listView or a
RecyclerView.

Clicking on any of the modules should present you with some resources available on that
module. You will have an api link to get this data as well and we will then be able to send
just the module code to this api link to get the data back from the API.

You should implement a Broadcast receiver to listen to the network state changes and
present the user with a no internet connection message as soon as they lose the internet
connection. You might also want to save the state of each session when an application is
closed so that the user doesn’t get presented with an empty screen when they launch the
application when the internet is turned off.
