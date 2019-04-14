# keyboard_listen 

监听键盘高度

## 用法

```dart

  KeyboardListen.init((data) {
         print(data["isVisible"]);//键盘是否弹出
         print(data["height"]);//键盘高度
  });

```