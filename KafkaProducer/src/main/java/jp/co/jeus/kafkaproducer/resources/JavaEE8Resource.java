package jp.co.jeus.kafkaproducer.resources;

import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author
 */
@Path("javaee8")
public class JavaEE8Resource {

    @GET
    public Response ping() {

        String topicName = "TestTopic";
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
//        props.put("bootstrap.servers", "192.168.99.100:9092");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, "1", "msg");

        producer.send(record, new org.apache.kafka.clients.producer.Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (metadata != null) {
                    String infoString = String.format(
                            "Success partition:%d, offser:%d",
                            metadata.partition(), metadata.offset());
                    System.out.println(infoString);
                } else {
                    String infoString = String.format("Failed:%s", e.getMessage());
                    System.err.println(infoString);
                }
            }
        });
        System.out.println("Message sent successfully");
        producer.close();

        return Response
                .ok("ping")
                .build();
    }
}
