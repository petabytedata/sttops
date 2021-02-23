package com.favoritemedium.sttops.service;

import com.favoritemedium.sttops.model.entity.STTResult;
import com.favoritemedium.sttops.model.repository.STTResultRepository;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for interacting with Speech-To-Text service provider. We intended to use Google APIs, for this.
 * As I was facing issue with GCP registration, and as an alternative called(as an Hystrix command) mocked service to return
 * dummy response.
 */
@Service
@RequiredArgsConstructor
public class STTService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final STTResultRepository sttResultRepository;

    @Value("${mock.url}")
    String url;

    /**
     * Calling mocked service to fetch dummy response from mocked upstream service.Here we have another fallback method,
     * which will be called in case there will be some issue with dependent or upstream service.
     *
     * @param data
     * @return
     */
    @HystrixCommand(fallbackMethod = "pushEventToKafka")
    public Optional<String> callSTT(byte[] data) {
        Map<?,?> map = restTemplate.getForObject(url, Map.class);
        if(map==null)
            return Optional.empty();
        return Optional.of((String)map.get("response"));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //TODO:Dummy method

    /**
     * Fallback method, which will get called in case callSTT Hystrix command failed to get response from its dependent
     * service. Here the intention is to make it fault tolerant and resilient with more retries.
     *
     * @param data
     * @return
     */
    public Optional<String> pushEventToKafka(byte[] data){
        return Optional.of("Attempting second call in 30 seconds");
    }

    public void createSTTResult(STTResult result){
        sttResultRepository.save(result);
    }

    //TODO:Not getting used

    /**
     * Copied as is from Google samples to call GCP's Speech-To-Text service.
     * @return
     * @throws Exception
     */
    public List<String> recognize()throws Exception{

        // Instantiates a client
        SpeechClient speech = SpeechClient.create();

        // The path to the audio file to transcribe
        String fileName = "/home/amit/voice.mp4"; // for example "./resources/audio.raw";

        // Reads the audio file into memory
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);

        // Builds the sync recognize request
        RecognitionConfig config =
                RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                        .setSampleRateHertz(16000)
                        .setLanguageCode("en-US")
                        .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = speech.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();
        List<String> responses = new ArrayList<>();
        for (SpeechRecognitionResult result : results) {
            List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
            for (SpeechRecognitionAlternative alternative : alternatives) {
                responses.add(alternative.getTranscript());
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
            }
        }
        speech.close();
        return responses;
    }
}