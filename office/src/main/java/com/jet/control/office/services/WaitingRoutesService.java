package com.jet.control.office.services;

import com.jet.control.common.beans.Route;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Service
@RequiredArgsConstructor
public class WaitingRoutesService {
    private final Lock lock = new ReentrantLock(true);
    private final List<Route> routes = new ArrayList<>();

    public void add(Route route) {
        try {
            lock.lock();
            routes.add(route);
        } finally {
            lock.unlock();
        }
    }

    public void remove(Route route) {
        try {
            lock.lock();
            routes.removeIf(route::equals);
        } finally {
            lock.unlock();
        }
    }
}
