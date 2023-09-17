import React, { useCallback, useEffect, useState } from "react";
import { Button, StyleSheet, View, Text } from "react-native";

import * as ExpoThumbHash from "expo-thumbhash";
import { encode } from "expo-thumbhash";


const knownThumbHash = [
  "1QcSHQRnh493V4dIh4eXh1h4kJUI",
  "3PcNNYSFeXh/d3eld0iHZoZgVwh2",
  "3OcRJYB4d3h/iIeHeEh3eIhw+j3A",
  "HBkSHYSIeHiPiHh8eJd4eTN0EEQG",
  "VggKDYAW6lZvdYd6d2iZh/p4GE/k",
  "2fcZFIB3iId/h3iJh4aIYJ2V8g",
  "IQgSLYZ6iHePh4h1eFeHh4dwgwg3",
  "YJqGPQw7sFltlqhFafSE+Q6oJ1h2iHB2Rw",
  "0BgGFwQEx1lrRnlxjQf2mZeJSa0G0lsA",
];

export default function App() {

  const [currentThumbHashIndex, setCurrentThumbHashIndex] = useState(0);

  const setNextThumbHash = useCallback(function setNextThumbHash() {
    setCurrentThumbHashIndex(curr => {
      return Math.min(knownThumbHash.length - 1, curr +1);
    });
  }, []);

  const setPrevThumbHash = useCallback(function setNextThumbHash() {
    setCurrentThumbHashIndex(curr => {
      return Math.max(0, curr - 1);
    });
  }, []);


  useEffect(() => {
    ExpoThumbHash.encode('https://avatars.githubusercontent.com/u/9294168?v=4').then(hash=>{
      console.log('hash =>', hash);
    })

  }, []);

  console.log('currentThumbHashIndex', currentThumbHashIndex)
  return (
    <View style={styles.container}>
      <ExpoThumbHash.ExpoThumbhashView
        onLoaded={() => console.log("onLoaded")}
        hash={knownThumbHash[currentThumbHashIndex]}
        style={{ width: "100%", height: 300 }} />
      <Text>{knownThumbHash[currentThumbHashIndex]}</Text>
      <View style={styles.row}>
        <Button title={"Previous"} onPress={setPrevThumbHash} />
        <Button title={"Next"} onPress={setNextThumbHash} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  row: {
    padding: 16,
    flexDirection: "row",
    width: "100%",
    justifyContent: "space-evenly"
  },
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center"
  }
});
