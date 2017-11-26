# Action-Button-Visualizer
Android application for visualizing Action Button trends around the world

## Inspiration

News is meant to convey information in the most efficient manner. Speakable (https://actionbutton.org) has created the Action Button which allows journalists to embed this tool into their news articles in order to elicit reader engagement. Using news articles with the Action Button, I have created a visualization tool (Action Button Visualizer) that places these news markers on a world map. Now, users can see the trends developing around the world and understand the scope of these issues.

## What it does

Users can pan across the Google Maps UI and click on icons (each icon corresponds to a different category of news). Once clicked, the marker will expand to show the news article title and a "click to read more" subtext. From there, if the user clicks on the marker, he/she will be redirected to the article's website.

## How I built it

Action Button Visualizer is built using Android studio and parses the data from a local JSON styled file. I used a local data file as opposed to web API since I could not find a Speakable API or a news API that fit my needs.

## Screenshots of the Application

Initial Loading Screen with Legend

![splashscreen](https://user-images.githubusercontent.com/23304836/33244297-b8071dd6-d2c2-11e7-8e8b-a03d3f5f1adb.png)

User Interface

![screenshot_20171126-121332](https://user-images.githubusercontent.com/23304836/33244325-216f086a-d2c3-11e7-8259-56f18d3b86f9.png)

After Clicking Marker

![screenshot_20171126-121343](https://user-images.githubusercontent.com/23304836/33244329-2bd0b952-d2c3-11e7-8a47-5de2418688d5.png)

After Clicking Another Marker

![screenshot_20171126-121349](https://user-images.githubusercontent.com/23304836/33244330-347aed20-d2c3-11e7-9c75-cd35207a167d.png)

Clicking Popup Window...

![screenshot_20171126-121431](https://user-images.githubusercontent.com/23304836/33244332-38409fe0-d2c3-11e7-9271-7c58de827070.png)

...and browser to article opens

![screenshot_20171126-121447](https://user-images.githubusercontent.com/23304836/33244334-3c555d3c-d2c3-11e7-9376-53a36b79bfd7.png)
