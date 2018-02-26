#/bin/bash

# first we compile the java apps
javac Split.java
javac Unshuffle.java
javac Cary.java

# split Cary's binary file into the various temps
java Split minirom_v3.bin

# unshuffle temp1-8 to give us the linear wavetable
java Unshuffle 

# and now we render out the linear wavetables to e352 wavs
java Cary jason.bin


