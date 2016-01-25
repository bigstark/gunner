# gunner
shoot sequentially



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
        compile 'com.github.bigstark:gunner:1.0'
}
```

##Usage
Add bullet annotation and set sequence and delay (delay is optional)
```
@Bullet(sequence = 1, delay = 1000)
private void someMethod() {
    // Something to do
}

@Bullet(sequence = 2)
private void someSecondMethod() {
    // Something to do
}
```

Execute method by sequence. Sequence must over 0, and delay too.
```
Gunner.shoot(this); // "this" is target that has method with annotation
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
