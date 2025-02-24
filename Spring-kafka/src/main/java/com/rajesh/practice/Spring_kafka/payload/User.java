package com.rajesh.practice.Spring_kafka.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private int id;

    private String firstName;

    private String lastName;
}
