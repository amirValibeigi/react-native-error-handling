import * as React from 'react';

import { StyleSheet, View, Button } from 'react-native';
import { logE, logS, logV, testError } from 'react-native-error-handling';

export default function App() {
  const onPress = React.useCallback(() => {
    logV('test', 1);
    logE('test', null);
    logS('test', undefined);
    logV('test', 'message');
    logV('test', { title: 'test v', main: 1 });
    logV('test', [{ title: 'test v', main: 1 }]);
    testError();
  }, []);

  return (
    <View style={styles.container}>
      <Button title="log" onPress={onPress} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
