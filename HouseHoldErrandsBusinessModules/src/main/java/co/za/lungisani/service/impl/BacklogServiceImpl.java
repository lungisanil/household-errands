package co.za.lungisani.service.impl;

import co.za.lungisani.domain.exceptions.InternalServerErrorException;
import co.za.lungisani.persistance.model.Backlog;
import co.za.lungisani.persistance.model.Item;
import co.za.lungisani.persistance.repository.BacklogRepository;
import co.za.lungisani.domain.exceptions.NotFoundException;
import co.za.lungisani.service.BacklogService;
import co.za.lungisani.translator.TeamServiceTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BacklogServiceImpl implements BacklogService {
    private final BacklogRepository backlogRepository;

    @Autowired
    public BacklogServiceImpl(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    @Override
    public Item retrieve(Long itemId) {
        return Optional.ofNullable(this.backlogRepository.findByItemId(itemId))
                .orElseThrow(() -> new NotFoundException("Cannot find the item"));
    }

    @Override
    public Backlog retrieveAll() {
        return Optional.ofNullable(TeamServiceTranslator.getBacklog(this.backlogRepository.findAll()))
                .orElseThrow(() -> new NotFoundException("Cannot find the backlog list"));
    }

    @Override
    public void remove(Long itemId) {
        try{
            this.backlogRepository.deleteByItemId(itemId);
        }catch (Exception exception){
            throw new NotFoundException("Cannot find the item");
        }
    }

    @Override
    public Item add(Item item) {
        try{
            return this.backlogRepository.save(item);
        }catch (Exception exception){
            throw new InternalServerErrorException("Cannot add the new item");
        }
    }

}
