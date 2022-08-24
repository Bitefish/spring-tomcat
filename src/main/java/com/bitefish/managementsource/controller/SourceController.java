package com.bitefish.managementsource.controller;

import com.bitefish.managementsource.components.ConfigComponent;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@AllArgsConstructor
@RequestMapping("/")
@RestController
public class SourceController {

    private final ConfigComponent configComponent;

    @GetMapping({"events"})
    public List<Document> getEvents() {

        // FROM WORDPRESS: CURL it!

        List<Document> rv = new ArrayList<>();

        ConnectionString cs = new ConnectionString(configComponent.getSourceConnection());
        MongoClient client = MongoClients.create(cs);
        MongoDatabase database = client.getDatabase(configComponent.getSourceDatabase());
        MongoCollection<Document> collection = database.getCollection("source-element-appointment-de");

        //db["source-element-appointment"].aggregate([{$lookup:{from:"source-element",localField: "sourceElement",foreignField: "_id",as: "element"}}])



        collection.aggregate(Arrays.asList(Aggregates.sort(Sorts.ascending("startDate")) ,Aggregates.lookup("source-element-de", "sourceElement", "_id", "element"))).forEach(doc -> rv.add(doc));



        return rv;
    }

    @GetMapping({"tours"})
    public List<Document> getTours() {

        List<Document> rv = new ArrayList<>();

        ConnectionString cs = new ConnectionString(configComponent.getSourceConnection());
        MongoClient client = MongoClients.create(cs);
        MongoDatabase database = client.getDatabase(configComponent.getSourceDatabase());
        MongoCollection<Document> collection = database.getCollection("source-element-appointment");

        // FILTER BY TOURS!!!!!

        //db["source-element-appointment"].aggregate([{$lookup:{from:"source-element",localField: "sourceElement",foreignField: "_id",as: "element"}}])
        collection.aggregate(Arrays.asList(Aggregates.lookup("source-element", "sourceElement", "_id", "element"))).forEach(doc -> rv.add(doc));

        return rv;
    }

    @GetMapping({"event/{event}"})
    public List<Document> getEventBySlug() {
        // FROM WORDPRESS: CURL it!

        List<Document> rv = new ArrayList<>();


        return rv;
    }


}
