### Written Answers: 20/26

#### Division Pseudocode
- The loop condition does not check if the running dividend is 0; if this doesn't exist, then an infinite loop is possible. (-1)

#### Weakened Rep Invariants
- 2.2: Changes in `getExpt` and `hashCode` are also required. `getExpt` would need to check for a zero coeff, and return zero if so. Note that if you change the `equals` method, you will most likely have to change the `hashCode` method. (-1)

- 2.3: You need not check the `equals` method if you've already considered the case in the constructor and `checkRep`

#### `checkRep`
- 1.3/2.1/3.1: Final immutable fields cannot be modified after they are instantiated; the compiler would complain about any attempt to do so.  Therefore, we can reason that `RatNum` and `RatTerm` cannot contain any bugs with regard to the representation invariant as long as we ensure the coherency of the data at initialization.  Therefore, `RatNum` and `RatTerm` are special cases that do not need calls to `checkRep` at the beginning and end of every public method, aside from the constructor. (-2)

- 1.3/2.1/3.1: Immutability is a property of the specification, and `checkRep` does not assume the specification was correctly implemented.  So, in general, regardless of whether or not they are immutable, ADTs need calls to `checkRep` at the beginning and end of all public methods. (-2)

### Code Quality: 3/3

### Mechanics: 3/3

### General Feedback
Great work! Look over the feedback to see when and where to use `checkRep`s and improve on them! :-)

### Specific Feedback
- Missing calls to `checkRep` at the beginning and end of every public method in `RatPoly` and/or `RatPolyStack`.
