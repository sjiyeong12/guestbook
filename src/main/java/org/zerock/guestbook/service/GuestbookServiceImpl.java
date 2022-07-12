package org.zerock.guestbook.service;

import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class GuestbookServiceImpl implements GuestbookService {
    GuestbookRepository gbRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        Guestbook entity = dtoToEntity(dto);

        return null;
    }
}