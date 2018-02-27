#/bin/bash

# first we compile the java apps
echo compiling apps

javac Unshuffle.java
javac Convert.java

# split Cary's binary file into the various temps
echo splitting the cary file into temps

java Split minirom_v3.bin

# unshuffle temp1-8 to give us the linear wavetable
echo unshuffling temps into linear wavetable file

java Unshuffle 

# and now we render out the linear wavetables to e352 wavs
echo rendering e352 wavs from linear wavetable

java Convert ppg_wavetables_linear.bin


