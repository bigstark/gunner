# gunner
[![travis](https://travis-ci.org/bigstark/gunner.svg?branch=master)](https://travis-ci.org/bigstark/gunner)
[![license](https://img.shields.io/hexpm/l/plug.svg)](LICENSE)
[![jitpack](https://img.shields.io/badge/jitpack-1.0-green.svg)](https://jitpack.io/#bigstark/gunner)

It can help you when you execute method sequentially.

If you have method with annotation, gunner execute method sequentially.

##Include your project
add build.gradle
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
```
dependencies {
        compile 'com.github.bigstark:gunner:1.1'
}
```

##Usage
Add bullet annotation and set sequence and delay (delay is optional)
```java
@Bullet(sequence = 1)
private void someMethod() {
    // Something to do
}

@Bullet(sequence = 2, delay = 1000)
private void someSecondMethod() {
    // Something to do
}

@Bullet(sequence = 3, delay = 2000)
private void someThirdMethod() {
    // Something to do
}
```

Execute method by sequence. Sequence must over 0, and delay too.
```java
Gunner.shoot(this); // "this" is target that has method with annotation
```


If you want to shoot multiple target, you can do like this.
```java
Gunner.shoot(target1, target2, target3)
```


License
-------

    Copyright 2016 BigStarK

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
