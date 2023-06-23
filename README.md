# react-native-error-handling

error handling

## Installation

clone project

```sh
npm install "{PATH}/react-native-error-handling"
```

MainApplication.java

```java
import com.errorhandling.utils.ExceptionHandler;
...

  @Override
  public void onCreate() {
    super.onCreate();

    Thread.setDefaultUncaughtExceptionHandler(
            new ExceptionHandler(
                    getBaseContext(),
                    "http://192.168.1.148:8000/api/app/report",//api post info, error, file
                    MainActivity.class,// run this activity
                    true//show button send error
            )
    );

    ...
  }
```

## Usage

```js
import { log, logV, logE, logS, testError } from 'react-native-error-handling';

// ...

log('E', 'name_log', 'message');
logV('name_logV', { message: 'text', other: '...' });
logE('name_logE', 1);
logS('name_logS');
testError(); //make crash divide by zero
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
