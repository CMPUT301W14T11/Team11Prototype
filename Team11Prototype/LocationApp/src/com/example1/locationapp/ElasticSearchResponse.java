package com.example1.locationapp;

/**
* This code has been taken and modified from:
* https://github.com/rayzhangcl/ESDemo
*
* @author Chenlei Zhang - Original Owner
* @author ya zhou jiang -Minor Editor
*/
public class ElasticSearchResponse<T> {
    String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    double max_score;
    public T getSource() {
        return _source;
    }
}
