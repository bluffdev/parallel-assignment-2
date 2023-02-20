# Assignment 2

The Minotaur's Birthday Party

## Build Steps

### Gradle

Gradle does not need to be installed on your computer

In the root directory run:<br> `./gradlew --console plain run`
<br>
The program will ask for input with this format:
<br>
`Enter Input: <problem> <guestCount>`
<br>
Problems

- birthday
- vase

Example input:
<br>
`birthday 100`
<br>
`vase 100`

### VS Code

If gradle is not working for some reason then you can run the project in vscode with the recommended java extensions installed. The user input steps are the same.

## Problem 1 Summary

### Strategy

I used Java's ReentrantLock to prevent multiple guests from going into the labyrinth at the same time. The guest that has the lock is able to enter the labyrinth and has access to the cupcake at the end. The strategy I used for communicating to the Minotaur that every guest entered the labyrinth at least once was to use a guest counter. The counter has a list of tags and each guest has one of the tags on that list which is used to identify them. When a new guest enters the labyrinth, the counter will mark off that guest by inserting their id into a set. The counter will be used to let the Minotaur know that every guest has entered the labyrinth so that he can end the party. I tested my code on a range of guests from 100 to 400 to verify that my implentation was effecient.

## Problem 2 Summary

### Strategy 1

This strategy allows any guest to check whether the showroom's door is open at anytime and try to enter the room. The main issue with this is that when a guest tries to acquire a lock, they keep attempting to grab the lock until the room is empty. This will effect performance since the threads are constantly bombarding the CPU by trying to acquire the lock.

### Strategy 2

This strategy uses a sign to indicate if the showroom is available or empty. This solves the problem with strategy one since a guest will only try to acquire the lock if the the sign says "AVAILABLE". It provides a better experience for the guests and will have better performance.

### Strategy 3

This strategy requires the guests to enter a line in order to enter the showroom. This line is represented as a queue. The main advantage to this strategy is that it guarantees that each guest will have a chance to enter the showroom. This is better than the second strategy since it does not prevent starvation. It will also have great performance like the second strategy

### Selected Strategy

I decided to try strategy 2 and 3 but I used 3 for my final submission. I left the sign lock in my repository for me to reference in the future. The main reason for choosing strategy 3 over strategy 2 is that it prevents starvation. The queue is represented using a CLH Lock which I got from the textbook. Guests will enter the queue once they try to acquire the lock. A guest has a 50% chance of wanting to enter the showroom at least once. After a guest enters, they will have a 50% of wanting to enter again. The program ends once ever guest has decided they do not want to enter the room anymore.
