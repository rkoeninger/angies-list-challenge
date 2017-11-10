[![Build Status](https://travis-ci.org/rkoeninger/angies-list-challenge.svg?branch=master)](https://travis-ci.org/rkoeninger/angies-list-challenge)

# Angie's List Challenge

Answers to interview questions for Scala role with Angie's List.

Time spent: ~4 hours

Parts 1-4 are completed in the following source files:

  1. [Fibonacci.scala](https://github.com/rkoeninger/angies-list-challenge/blob/master/src/main/scala/Fibonacci.scala)
  2. [Poker.scala](https://github.com/rkoeninger/angies-list-challenge/blob/master/src/main/scala/Poker.scala)
  3. [DataStructures.scala](https://github.com/rkoeninger/angies-list-challenge/blob/master/src/main/scala/DataStructures.scala)
  4. [FPPractice.scala](https://github.com/rkoeninger/angies-list-challenge/blob/master/src/main/scala/FPPractice.scala)

Part 5 is answered below.

### Building and Tests

Code can be found in `./src/main/scala`. It can be built and tested with SBT.

## Functional Programming Theory

  1. What makes a function pure? Give examples.
  
    A function is pure if it does not affect or depend on its environment. Its return value can only vary with respect to the arguments passed in.

    Classic examples of pure functions are mathematical functions and operators: addition, exponentiation and absolute value. `toString()`methods are also typically pure.

    Note that a function can still be considered apparently pure if it uses certain impurities internally - like mutable data structures.

  2. What is referential transparency? What are its benefits?
  
    Referential transparency refers to the property of a function that its application to a set of arguments can be freely replaced with its result.

    For example, given values `x` and `y`, one could replace all occurances of `x + y` in that scope with `z` where `let z = x + y`. Note that this could not be done with a non-RT function like `readLine`, which is impure and will return different values for the same arguments.

    Referential transparency greatly aides programmer comprehension by enabling equational reasoning and use of the subsitution method. With RT functions, one can freely substitute `f(x, y)` with `z` where `let z = f(x, y)` and vice-versa.

    Code is also easier to re-use as side-effects are not guaranteed to be composable and often must be performed in a specific order or a specific number of times. These problems are related to the problems caused by using global variables in a program.

  3. How does a functional program deal with side-effects?

    An FP approach to handling side-effects usually involves isolating and minimizing them as much as possible. Or, in the case of Haskell, it could use immutable, composible values that represent side-effects and return them up the call chain to be evaluated beyond the `main` function.