package com.rajesh.practice.producer;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.rajesh.practice.changeHandler.WikiMediaChangeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WikiMediaChangeProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    public void sendMessage(){
        String topic = "wikimedia_recentchange";

        // To read realtime stream data from wikimedia, we use event source
        BackgroundEventHandler eventHandler = new WikiMediaChangeHandler(kafkaTemplate, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // Step 1: Create an EventSource.Builder
        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(url));

        // Step 2: Create a BackgroundEventSource.Builder using the EventSource.Builder
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder);

        // Step 3: Build the BackgroundEventSource
        BackgroundEventSource eventSource = builder.build();

        // Keep the main thread alive for a while to allow the event source to run
        try (eventSource) { //here we are using try with resource to automatically close the resource
            // Start the event source in a separate thread
            eventSource.start();
            TimeUnit.MINUTES.sleep(10); // Adjust the sleep time as needed
        } catch (InterruptedException e) {
            log.error("Thread was interrupted", e);
        }
    }

}
