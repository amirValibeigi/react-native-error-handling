import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-error-handling' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ErrorHandling = NativeModules.ErrorHandling
  ? NativeModules.ErrorHandling
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function log(key: 'E' | 'S' | 'V', name: string, message?: any): void {
  let _mes = '';

  if (message === undefined || message === null) {
    _mes = '';
  } else if (typeof message === 'object') {
    _mes = JSON.stringify(message);
  } else if (message instanceof Array) {
    _mes = JSON.stringify({ log: message });
  } else {
    _mes = String(message);
  }

  ErrorHandling.log(key, name, _mes);
}

export function logV(name: string, message?: any): void {
  log('V', name, message);
}

export function logE(name: string, message?: any): void {
  log('E', name, message);
}

export function logS(name: string, message?: any): void {
  log('S', name, message);
}

/**
 * Exception divide by zero
 */
export function testError(): void {
  ErrorHandling.testError();
}
