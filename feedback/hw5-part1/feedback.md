### Written Answers: 20/20

### Design: 2/3

### Documentation & Specification (including JavaDoc): 3/3

### Testing (test suite quality & implementation): 2/3

Some of your junit tests that should be script tests

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback

Great work! Your design is generally pretty good, but there are a few choices
you made that I don't quite see how it will work out in implementation. There
are also a few issues with the details of your test suite, but in general you
did really good job of test coverage.

#### More Details

I docked a point from your design because the `getEdges` method wouldn't work
effectively as currently specified. Edges require data about start and end
nodes as well as the label of the edge, which may be different than the end
node. The label may not even have the same type of data as the nodes.

Another note about design is the edge class not having a way to get the start
node. I'm not entirely sure how you plan on using this in implementation so it
could very well be that it could work, but I could see how you might run into
problems say, if you just wanted to list all the edges in the graph.

