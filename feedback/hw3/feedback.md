### Written Answers: 5/6

### Code Quality: 3/3

### Mechanics: 3/3

### General Feedback

Great work! Take a look at the specific comments below for next week.

### Specific Feedback

testBaseCase failed for the same reason that testThrowsIllegalArgumentException
did, but for no other reason.  The fix identified for testBaseCase belongs as a
fix for testInductiveCase instead.

When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.

Missing documentation for the new fields in `BallContainer.java` and/or `Box.java`.
Make sure to document new additions in the future!

Your BallContainer add/remove methods are more complicated than they need to be.
Look at the documentation for `Set.add` and `Set.remove` and see whether you
need to explicitly handle cases the cases of adding something that already
exists in the set and removing something that doesn't exist in the set.

A better way to write ball comparison is to use `Double.compare`.  Check
the Java documentation of these methods to see how you might use them.
