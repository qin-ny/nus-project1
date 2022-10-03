package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.CanteenType;

import java.util.List;

public interface CanteenTypeService {
    public int create(String type);
    public List<CanteenType> get();
    public int update(Integer id, String type);
    public int delete(Integer id);
}
