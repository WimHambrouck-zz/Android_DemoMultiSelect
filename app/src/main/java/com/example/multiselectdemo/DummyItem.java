package com.example.multiselectdemo;

public class DummyItem {
    private String naam;

    public DummyItem(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return this.naam;
    }
}
