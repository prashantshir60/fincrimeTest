package com.sebembedded.fincrimeinterview.model;

import com.sebembedded.fincrimeinterview.model.enumeration.Occupation;

public record User(String id, String firstName, String lastName, Occupation occupation) {

}
