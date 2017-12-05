# IoT Producer

## Installation
### Dependencies
In order to be able to download, build and run this project, make sure you have [Maven](https://maven.apache.org/) and [git](https://git-scm.com/) installed.
### Download
Once installed the dependencies, clone/checkout the project repository using the terminal:

```
git [clone|checkout] https://gitlab.intre.it/youStream/youstream-iot-producer.git
```

### Build
Once downloaded the project, navigate to the root folder and install it using the following commands in the terminal:

```
cd youstream-iot-producer/
mvn [clean] install
```

## Execution
From the project root folder in the terminal, create and run a simulated device using:

```
mvn exec:java -Dexec.args="[DEVICE_ID]"
```

where **DEVICE_ID** corresponds to the kind of device you want to simulate, e.g. "LBM" for a *Light Bulb Monitor* device.

Once started the execution, the simulated devices can be interrupted using **CTRL+C**.

### Simulated Devices
The current version of the project supports the following **DEVICE_ID**s:

|ID|Device|
| :---: | :---: |
|PTS|Perceived Temperature Sensor|
|ADC|Analog-to-Digital Converter|
|DAC|Digital-to-Analog Converter|
|LBM|Light Bulb Monitor|
|EMC|Electric Motor Controller|
|BSM|Battery Status Monitor|