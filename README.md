# CheckHost4J

Java implementation of the check-host.net API

## Use in Gradle

If you want to depend on CheckHost4J in your own project, use the Maven repository here:

https://mvnrepository.com/artifact/de.florianreuth/checkhost4j

or

https://maven.florianreuth.de/#/snapshots/de/florianreuth/checkhost4j (for snapshots)

The repository page includes the latest coordinates and setup instructions.

Jar builds can be downloaded from my build server: https://build.florianreuth.de/job/CheckHost4J/

## Requirements

- [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1)

## API Terminology

The API main class is `CheckHost4J`, it contains all the methods to interact with the API.

```java
final CheckHost4J checkHost = CheckHost4J.INSTANCE;
```

You can either use the `CheckHost4J.INSTANCE` or create a new instance of `CheckHost4J` yourself, by creating an own
instance you can define the `IRequester` yourself, which is used to send the requests to the API.

```java
final CheckHost4J checkHost = new CheckHost4J(...);
```

The default `IRequester` is `JavaRequester`, which can be accessed via `JavaRequester.INSTANCE`, you can also create a
new instance using `new JavaRequester("<user-agent>")`.

```java
final CheckHost4J checkHost = new CheckHost4J(new JavaRequester("MyUserAgent"));
```

You can use the methods `CheckHost4J#ping`, `CheckHost4J#http`, `CheckHost4J#tcpPort`, `CheckHost4J#udpPort`
and `CheckHost4J#dns`
to get a `ResultNode<T>` where T is the result type of the request (e.g. `PingResult`, `TCPResult`).

```java
final ResultNode<PingResult> pingResult = checkHost.ping("example.com", 80 /* max nodes */);
```

After you got the `ResultNode<T>` you can use the `tickResults()` to update the `getResults()` list.

```java
// This will update the results list by sending the check-result request to the API,
// This might not update all results, because some might not be finished yet
// Which means you have to call this method multiple times to get all results (e.g. with a delay of 5 seconds)
pingResult.tickResults();

final Map<ServerNode, PingResult> results = pingResult.getResults();
results.forEach((serverNode, result) -> {
    if (result == null) { // All results which are not finished yet will be null
        System.out.println(serverNode.name + " is still checking...");
    } else if (result.getErrorMessage() != null) {
        System.out.println(serverNode.name + " failed: " + result.getErrorMessage());
    } else {
        System.out.println(serverNode.name + " responded: " + result.getSuccessfulPings() + "/" + result.getTotalPings());
    }
});
```

You can also get all the server nodes which are being checked by using the `getNodes()` method.

```java
final List<ServerNode> nodes = pingResult.getNodes();
```

The `de.florianreuth.checkhost4j.model.result` package contains all the result classes, which are used to store the
result of the requests.

To get a list of all Request types you can use the `ResultType` enum.

## Contact

- Issues: https://github.com/florianreuth/CheckHost4J/issues
- Discord: https://florianreuth.de/discord
