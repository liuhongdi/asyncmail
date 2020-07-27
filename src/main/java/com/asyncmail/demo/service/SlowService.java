package com.asyncmail.demo.service;

import java.util.List;
import java.util.concurrent.Future;

public interface SlowService {
    public void sleepawhile();
    public Future<String> asyncsleepawhile(int i);
}
