# assessment project

This implementation assumes that Kafka is installed locally.

Solution proposal for the additional requirement:
1. Use the group attribute of the article as a message key - Kafka can guarantee the ordering as the messages with the same key will be sent to the same partition

2. Kafka Streams uses the TimeStampExtractor interface to get the timestamp from the current record.
In order to achieve ordering by arrival time in the REST endpoint, a custom TimeStampExtractor should be implemented in order to retrieve timestamps embedded in the payload of messages 
For this purpose, ArticleRequest should be extended with a timestamp member variable in order to hold the arrival time in the REST endpoint

3.
option a) define the custom TimeStampExtractor for reading the input topic as a stream via Consumed.with(timestampExtractor)

option b) KafkaStreamsConfiguration - add to the property configuration a property with key StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG and value the custom TimeStampExtractor

