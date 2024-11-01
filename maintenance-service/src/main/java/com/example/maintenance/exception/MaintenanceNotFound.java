package com.example.maintenance.exception;

import com.example.maintenance.entities.Maintenance;

public class MaintenanceNotFound extends Exception {
    public MaintenanceNotFound(String m){super(m);}
}
