# Farkle for Kindle (KDK)

A Farkle dice game for Amazon Kindle e-reader devices, built with the Amazon Kindle Development Kit (KDK).

Copyright © 2013 Olmatech Technology

Overview

This project is a Kindlet, a Java application that runs on supported Kindle e-readers. It brings Farkle, the classic push-your-luck dice game, to the Kindle's e-ink screen and includes a high score feature.

High score setup

How to Play

Farkle is played with six dice. The general rules are:

On your turn, roll all six dice.
Set aside at least one scoring die or combination, then choose to either bank your points or roll the remaining dice for a chance at more.
If a roll has no scoring dice, that is a Farkle and you lose all points accumulated during that turn.
If all six dice score, you can pick them all up and keep rolling ("hot dice").
The first player to reach the target score wins.

Repository Structure
Path	Description
src/	Java source code for the Kindlet
MANIFEST.MF	Kindlet manifest describing the application
kindlet.properties	Kindlet configuration properties
build.xml	Ant build script for the project
high_score_setup.png	Screenshot of the high score setup screen
.gitignore	Git ignore rules
Requirements
A Kindle e-reader device that supports KDK applications
Java JDK (the KDK targets an older Java platform, so check the KDK documentation for the supported version)
Apache Ant to run the build script
Amazon Kindle Development Kit (KDK) libraries and tools
Building

The project uses an Ant build script. After installing the KDK and Ant, update any KDK or signing paths in build.xml for your machine, then run:

bash
ant -f build.xml

Installing on a Kindle
Build the Kindlet package as described above.
Connect the Kindle to your computer over USB.
Copy the built package to the Kindle's documents folder.
Disconnect the device. The game should appear in your Kindle library.

Notes

KDK applications generally need to be signed before they can run on a device. See the Amazon KDK documentation for details on keystores and signing.

License

Copyright © 2013 Olmatech Technology. All rights reserved.