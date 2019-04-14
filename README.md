# keyboard_listen 

监听键盘高度

## 用法
```
keyboard_listen:
    git:
      url: git@github.com:woddp/keyboard_listen.git
```
```dart

  KeyboardListen.init((data) {
         print(data["isVisible"]);//键盘是否弹出
         print(data["height"]);//键盘高度
  });

```
