package com.example1.locationapp;

import java.util.Collection;

import InternetConnection.ElasticSearchResponse;

/**
 * this is GSP method using for loaction in our app
* This code has been taken and modified from:
* https://github.com/rayzhangcl/ESDemo
*
* @author Chenlei Zhang - Original Owner
* @author Ya Zhou Jiang -Minor Editor
*/
public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}