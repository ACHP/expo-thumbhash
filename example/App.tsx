import { StyleSheet, Text, View } from 'react-native';

import * as ExpoThumbhash from 'expo-thumbhash';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>{ExpoThumbhash.hello()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
