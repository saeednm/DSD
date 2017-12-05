YouSTREAM Project Instructions


In order to successfully run the YouSTREAM component follow these steps:

1) Start Zookeeper Server:(from kafka directory)
	
	-	bin/zookeeper-server-start.sh config/zookeeper.properties

	 for Windows OS:

	-	bin/windows/zookeeper-server-start.bat config/zookeeper.properties

2) Start Kafka Server:

	-	bin/kafka-server-start.sh config/server.properties

	 for Windows OS:
	-	bin/windows/kafka-server-start.bat config/server.properties

3) Start YouSTREAM Project (Plugin Manager):
	
	- mvn spring-boot:run

	Run this command inside YouSTREAM project directory

4) Add plugin by running plugin project, it should be detected in Plugin manager that plugin is registered:
	
	```
	cd YouStream Plugin Bitbucket/
	```

	- Previous the plugin must be created with desired properties set inside in dataTransformer method inside src/main/java/com/youscada/AppConfig.java 
	- Examples can be found in unit test files which can be found inside folder src/main/java/com/youscada/core/tests 

4) Start IOT Producer:

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


In order to see field data stream run next command in new terminal window:

	- bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic fieldRawDataTopic --from-beginning

	 for Windows OS:

	- bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic fieldRawDataTopic --from-beginning


In order to see transformed data stream run next command in new terminal window:

	- bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic  transformedDataTopic --from-beginning

	for Windows OS:

	- bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic  transformedDataTopic --from-beginning

In order to see WebPage or GUI of YouSTREAM project go to youstream-admin project:

	- npm install

	- npm scripts/app.js	

	Node server is running at port 3000.
	On the webpage it can be seen which plugins are registered with on/off option.


