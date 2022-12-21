package com.example.uiju;


import lombok.Builder;
import lombok.NonNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class DemoModel {

    @NonNull
    private String name;
}
