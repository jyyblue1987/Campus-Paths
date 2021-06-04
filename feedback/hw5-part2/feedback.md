### Written Answers: 10/10

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF): 2/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback
Nice work-- keep it up!

#### More Details
- Note that in your `containsNode` method, you can probably replace that `if/else`
structure with `return graph.containsKey(node)` (and similarly for the `containsEdge`
method).
- For the `removeNode` method, what was your reasoning for iterating `graph.size()`
number of times + the logic inside of that `for` loop?
- In your `checkRep` method, you may also want to check that the graph is not `null`
(since that is part of your representation invariant).
- Your `checkRep` might cause performance issues on big datasets.  Be sure to
disable the portions of your `checkRep` that involve `for` loops and other
costly operations by using a `static final boolean` "debug flag" that is set to
`true` while you're still developing and testing, and set to `false` while
you're running big tests and submitting.
