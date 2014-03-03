# Voting Simulator

Copyright © 2011-2014 Remy Oukaour

Inspired by [Ka-Ping Yee's visualizations](http://www.zesty.ca/voting/sim/) and by [Warren D. Smith's Infinitely Extendible Voting Simulator](http://www.rangevoting.org/IEVS/Pictures.html).

This program simulates elections using various electoral systems to compare the systems' relative merits.

## Algorithm

To simulate elections, this program makes some simplifying assumptions:

 * Political positions can be represented as points on a 2D grid. I think of the x- and y-axes as measuring economic and social freedom, as with the Political Compass <politicalcompass.org>, but what they represent is irrelevant.
 * Voters are honest: they prefer candidates who are closer to their own positions.
 * In a single election, voters are normally distributed around a "center of opinion."

The user sets the election parameters, fills out a ballot with candidates, and runs a series of elections. An election is held for every pixel on the image, with the pixel's coordinates as its center of opinion. Each pixel is colored according to the winner.

## Menus

Every menu option has a corresponding keyboard shortcut.

 * Election menu:
    * New (Ctrl+N): Initializes the controls with their default values.
    * Load... (Ctrl+L): Loads saved election parameters from a .election file.
    * Save... (Ctrl+Shift+S): Saves the current election parameters as a .election file.
    * Start (Ctrl+R): Starts running a series of elections, or resumes the current series if it was paused. Disabled if the ballot is empty.
    * Pause (Ctrl+P): Pauses the current election series. Disabled if there is no series running.
    * Stop (Ctrl+T): Stops the current election series. Disabled if there is no series started.
 * Image menu:
    * Clear (Ctrl+C): Clears the image.
    * Save... (Ctrl+S): Saves the image. The following formats may be supported by your system: PNG, GIF, BMP, TGA, TIFF, PPM.
 * Ballot menu:
    * Add (Ctrl+D): Adds a candidate with the given color and location to the ballot.
    * Clear (Ctrl+Shift+C): Removes all candidates from the ballot.
 * Help menu:
    * Help (F1): Shows this help file.
    * About (Ctrl+A): Shows an About dialog with copyright information.

## Controls

 * Image: Click on the image to add a randomly-colored candidate at that location to the ballot.
 * Electoral system: Choose an electoral system to use for the elections. Descriptions of each system are below.
 * Population: Set the population size for each election. Should be a positive integer.
 * Variance: Set the variance for each election. Should be between 0 and 1. Elections use a normal distribution of voters.
 * Color: Set a candidate's color. Should be a hexadecimal RGB triple. This color will be used in the image if the candidate wins an election. Colors are represented as hexadecimal triples of red, green, and blue.
 * Location: Set a candidate's location in the space of political opinions. The first field is the x coordinate, the second is y. The image only displays points with coordinates between −0.25 and 1.25, but candidates can be placed anywhere.
 * Add: Adds a candidate with the given color and location to the ballot.
 * Remove: Removes the selected candidate from the ballot. Disabled if no candidate is selected.
 * Update: Updates the selected candidate with the given color and location. Disabled if no candidate is selected.
 * Stop: Stops the current election series. Disabled if these is no series started.
 * Elect/Pause: Starts or pauses a series of elections. Disabled if the ballot is empty.

## Electoral systems

An electoral system is a method for voters to express their preferences, and for a single winning candidate to be chosen from those preferences. For a given ballot, different electoral systems may give very different results. This program's purpose is to compare systems.

In case of a tie, a random winner is chosen.

 * Plurality: Each voter votes for one candidate. The candidate with the most votes wins. This is a common electoral method, and is the one used to choose U.S. presidents.
 * Anti-plurality: Each voter votes against one candidate. The candidate with the fewest votes wins.
 * For-and-against: Each voter votes for one candidate and against one candidate. The candidate with the most votes wins.
 * Dabagh: Each voter votes twice for their most-preferred candidate and once for their second-most-preferred candidate. The candidate with the most votes wins.
 * Bucklin: Each voter ranks the candidates according to preference. If a candidate has a majority of the first-choice votes, they win. Otherwise, second-choice votes are added to the first-choice votes, then third-choice, and so forth until a candidate has a majority.
 * Majority judgment: Each voter ranks the candidates according to preference. The candidate with the highest median rank wins.
 * Approval: Each voter votes for every candidate of whom they approve. The candidate with the most votes wins. (For the purposes of this program, voters approve of candidates who are within an acceptable distance, chosen randomly for each voter from a log-normal distribution with mean 0 and variance 0.25.)
 * Approval (strategic): Identical to the approval method above, except that voters approve of candidates who are closer than average to their own position.
 * Borda: Each voter ranks the candidates according to preference. First-choice candidates get one point, second-choice two, and so forth. The candidate with the fewest points wins.
 * Nauru: Each voter ranks the candidates according to preference. First-choice candidates get one point, second-choice one-half, and so forth. The candidate with the most points wins.
 * Instant runoff: Each voter ranks the candidates according to preference. If a candidate has a majority of the first-choice votes, they win. Otherwise, the candidate with the fewest first-choice votes is eliminated, and their second-choice votes are distributed among the remaining candidates. This process continues until a candidate has a majority.
 * Coombs: Each voter ranks the candidates according to preference. If a candidate has a majority of the first-choice votes, they win. Otherwise, the candidate with the most last-choice votes is eliminated, and their second-choice votes are distributed among the remaining candidates. This process continues until a candidate has a majority.
 * Contingent: Each voter ranks the candidates according to preference. If a candidate has a majority of the first-choice votes, they win. Otherwise, all but the top two candidates are eliminated, and their second-choice votes are distributed among the remaining two candidates. Then the candidate with the most votes wins.
 * Concordet: Each voter ranks the candidates according to preference. If a candidate is preferred to all other candidates, that candidate wins. There are many methods to decide an election in which there is no such winner; this program uses the Schulze method.
 * Range: Each voter gives each candidate a score according to preference. The candidate with the lowest score wins. (For the purposes of this program, voters use the distance between themselves and each candidate as that candidate's score.)
 * Random dictator: The candidate preferred by a random voter wins.
 * Random pair: The ballot is limited to two random candidates. Each voter votes for one candidate. The candidate with the most votes wins.
 * Sortition: A random candidate wins.
 * Optimal: The best candidate wins.
 * Pessimal: The worst candidate wins.

[Wikipedia](http://en.wikipedia.org/wiki/Electoral_method) has more detailed information about these and other electoral methods.
