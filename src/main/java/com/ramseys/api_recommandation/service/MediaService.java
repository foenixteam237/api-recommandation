package com.ramseys.api_recommandation.service;

import org.springframework.stereotype.Service;

import com.ramseys.api_recommandation.model.Media;
import com.ramseys.api_recommandation.repository.MediaRepository;

import java.util.List;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<Media> getMediaByTags(List<Long> tagIds) {
        return mediaRepository.findByTagIds(tagIds);
    }
}