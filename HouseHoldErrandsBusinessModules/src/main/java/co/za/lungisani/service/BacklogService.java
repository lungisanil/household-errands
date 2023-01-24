package co.za.lungisani.service;

import co.za.lungisani.persistance.model.Backlog;
import co.za.lungisani.persistance.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface BacklogService extends GenericService<Item, Integer, Backlog> {

}
