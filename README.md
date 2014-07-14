# Dynamic routing example

See the blog post here:
[link](#)

## Running

Build and run with

```
$ sbt run
```

When running, you can send requests to the endpoints, for example using curl

```
$ curl localhost:8080/echo/?a=1
Map(a -> 1)
$ curl localhost:8080/theatres/1
{
  "id": 1,
  "name": "Theatre #1"
}
```

## Testing

Build and test with

```
$ sbt test
```
