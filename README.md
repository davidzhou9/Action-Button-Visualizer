# Action-Button-Visualizer
Android application for visualizing Action Button trends around the world

## Inspiration

News is meant to convey information in the most efficient manner. Speakable (https://actionbutton.org) has created the Action Button which allows journalists to embed this tool into their news articles in order to elicit reader engagement. Using news articles with the Action Button, I have created a visualization tool (Action Button Visualizer) that places these news markers on a world map. Now, users can see the trends developing around the world and understand the scope of these issues.

## What it does

Users can pan across the Google Maps UI and click on icons (each icon corresponds to a different category of news). Once clicked, the marker will expand to show the news article title and a "click to read more" subtext. From there, if the user clicks on the marker, he/she will be redirected to the article's website.

## How I built it

Action Button Visualizer is built using Android studio and parses the data from a local JSON styled file. I used a local data file as opposed to web API since I could not find a Speakable API or a news API that fit my needs.

## Screenshots of the Application

https://github.com/davidzhou9/Action-Button-Visualizer/issues/1
