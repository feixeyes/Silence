package com.feixeyes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.UUID;


import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 2017/9/15.
 */
public class KafkaProducerDemo {
    private static final  String BROKER_LIST = "localhost:9092";
    private static final  String TOPIC = "kafkatopic";


    public static void main(String[] args) throws InterruptedException{
        System.out.println("Kafka Producer!");
        Properties props = new Properties();
        props.put("bootstrap.servers", BROKER_LIST);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //生产者发送消息
        Random random = new Random();
        int j = random.nextInt(20);
        while (true) {
            j--;
            if(j==0){
                Producer<String, String> procuder = new KafkaProducer<String,String>(props);
                String value = UUID.randomUUID().toString();
                ProducerRecord<String, String> msg = new ProducerRecord<String, String>(TOPIC, value);
                procuder.send(msg);
                procuder.close(100, TimeUnit.MILLISECONDS);
                j = random.nextInt(20);
            }
            TimeUnit.SECONDS.sleep(random.nextInt(2));
        }


    }
}
