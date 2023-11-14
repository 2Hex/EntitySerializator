# EntitySerializer
[![](https://jitpack.io/v/2Hex/EntitySerializer.svg)](https://jitpack.io/#2Hex/EntitySerializer)
[![CodeFactor](https://www.codefactor.io/repository/github/2hex/entityserializer/badge)](https://www.codefactor.io/repository/github/2hex/entityserializer)

Serializes Entities, Basically converting them to namespacedkeys that can be used later, not exactly serializing with its common definition.

**Note:**
- This will only work for 1.17.1+
- Only use classes EntitySerializer, and classes inside api package.

Easily turn an entity into a namespacedkey of the name of your choice, then turn it back into an entity.

**Credits to: _gizmonster_** for giving me the idea of using StructuresAPI Introduced in 1.17.1

# Installation
**For MAVEN:**
Add this to your repositories
```HTML
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Then add this to your dependencies
 ```HTML
<dependency>
     <groupId>com.github.Dqmino</groupId>
    <artifactId>EntitySerializer</artifactId>
    <version>v1.1</version>
</dependency>
```

**For GRADLE:**

Use this:

  ```HTML
	allprojects {
repositories {
...
maven { url 'https://jitpack.io' }
}
}
```
And the dependency:

```HTML
	dependencies {
implementation 'com.github.2Hex:EntitySerializer:Tag'
}
```

# Usage

First, you have to create an object of EntitySerializer in your onEnable:

```Java
EntitySerializer entitySerializer = EntitySerializer.create(this);
```

Then make a variable, referencing the class EntityHandler:

```Java
private static EntityHandler handler;
```

then on your onEnable again:

```Java
handler = entitySerializer.getEntityHandler();
```

Then make a static getter:

```Java
public static EntityHandler getHandler() {
        return handler;
}
```
Then later in whatever class, you can use
```Java
NamespacedKey namespacedkey = new NamespacedKey(pluginInstance, "your-key-here");
YourMainClass.getHandler().serialize(entity, namespackedkey);
EntityHolder entityDeserialized = YourMainClass.getHandler().deserialize(namespacedkey);
```
**Note that you should have a unique namespacedkey for each unique entity serialization.**

You can invoke `spawnAndGet()` (which will return the entity and spawn it) on the EntityHolder later, or `spawn`.


And to Destroy a serialization (Remove its access, and its place in server storage) Use:
```Java
YourMainClass.getHandler().destroy(namespacedkey)
```
