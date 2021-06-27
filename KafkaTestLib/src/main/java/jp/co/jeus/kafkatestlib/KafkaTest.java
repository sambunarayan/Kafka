/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.jeus.kafkatestlib;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author soyou
 */
public class KafkaTest {

    public static void main(String[] args) {
        String topicName = "TestTopic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.99.100:9092");

        props.put("retries", 0);

        props.put("batch.size", 16384);

        props.put("linger.ms", 1);

        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>(topicName,
                    Integer.toString(i), Integer.toString(i)));
        }
        System.out.println("Message sent successfully");
        producer.close();
    }
}
