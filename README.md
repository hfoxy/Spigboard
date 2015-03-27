Spigboard
=========

A simple scoreboard manager library for Spigot plugins.
Credit to RainoBoy97 is mentioned in the location where his code is used :)

## Using Spigboard in your project
### Maven

**Repository**
```xml
<repository>
    <id>komp-repo</id>
    <url>http://repo.kompking.info/nexus/content/groups/public</url>
</repository>
```

**Dependency**
```xml
<dependency>
    <groupId>me.hfox</groupId>
    <artifactId>spigboard</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

You will have to shade this dependency if you don't expect it to be compiled in any other plugins

### Creating a Spigboard

```java
Spigboard board = new Spigboard("my scoreboard");
```

### Add players to a Spigboard

```java
board.add(player);
```

### Adding scores to a Spigboard

If you would like to overwrite existing scores with the supplied name
```java
board.add("kills", 0, true);
```

Create a score with the supplied name, if the that name already exists, attempt to create another score with the same name
"key" is the key used to reference this score later on if you wish to update it.
You can also use an Enum, this will use the `Enum#name()` method as the key
```java
board.add("key", "kills", 0);
```

Any `Spigboard#add()` methods that create Scores will return a score, you can store this to update later

### Updating existing scores

If you have the Score you want to update
```java
score.update("deaths");
```

If you don't have the score you want to update, you should check if the score is null or not, incase the key is not present.
```java
Spigboard score = spigboard.getEntry("key");
if (score != null) {
    score.update("deaths");
}
```

In some cases, you may not know the key for a score. If the score is unique and less than or equal to 48 characters, you can use `Spigboard#getEntryByName(name)`