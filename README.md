# Multi-variant publishing with Gradle

This project demonstrates how to publish a library which has different dependencies based on the consumer JDK version.

The `lib` directory defines a library which needs Nashorn as an external dependency, but only on JDK 16+.

The `consumer` directory defines a consumer of that library.

To see what dependencies would be found using Java 11 on the consumer, run:

    ./gradlew consumer:dependencies --configuration compileClasspath

```
`------------------------------------------------------------
Project ':consumer'
------------------------------------------------------------

compileClasspath - Compile classpath for source set 'main'.
\--- project :lib
     \--- org.apache.commons:commons-math3:3.6.1
```

Now do the same but with Java 16:

    ./gradlew -Dversion=16 consumer:dependencies --configuration compileClasspath

```
------------------------------------------------------------
Project ':consumer'
------------------------------------------------------------

compileClasspath - Compile classpath for source set 'main'.
\--- project :lib
     \--- org.openjdk.nashorn:nashorn-core:15.0
          +--- org.ow2.asm:asm:7.3.1
          +--- org.ow2.asm:asm-commons:7.3.1
          |    +--- org.ow2.asm:asm:7.3.1
          |    +--- org.ow2.asm:asm-tree:7.3.1
          |    |    \--- org.ow2.asm:asm:7.3.1
          |    \--- org.ow2.asm:asm-analysis:7.3.1
          |         \--- org.ow2.asm:asm-tree:7.3.1 (*)
          +--- org.ow2.asm:asm-tree:7.3.1 (*)
          \--- org.ow2.asm:asm-util:7.3.1
               +--- org.ow2.asm:asm:7.3.1
               +--- org.ow2.asm:asm-tree:7.3.1 (*)
               \--- org.ow2.asm:asm-analysis:7.3.1 (*)

(*) - dependencies omitted (listed previously)
```

You can see what would be published on Maven by running:

    ./gradlew pAPTBR

and look at the `lib/build/repo` directory. In particular, take a look at the `.module` file.
